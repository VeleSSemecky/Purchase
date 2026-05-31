package com.veles.purchase.domain.usecase.scanner

import com.veles.purchase.domain.model.scanner.ScannedProduct
import com.veles.purchase.platform.extractor.MoneyAnnotation
import com.veles.purchase.platform.extractor.PriceEntityExtractor
import kotlinx.coroutines.CancellationException

class ParsePriceTagUseCase(private val entityExtractor: PriceEntityExtractor) {

    suspend operator fun invoke(lines: List<String>): ScannedProduct {
        val fullText = lines.joinToString("\n")
        val name = buildProductName(lines)

        // Try entity extraction first (ML Kit on Android, regex on iOS)
        try {
            val entities = entityExtractor.extractMoneyEntities(fullText)
            if (entities.isNotEmpty()) {
                val (amount, currency) = selectTotalPrice(fullText, entities)
                return ScannedProduct(name = name, price = amount, currency = currency)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            // Fall through to regex backup
        }

        // Regex backup (extra safety net)
        return regexFallback(lines, name)
    }

    /**
     * Picks the "Cena:" price over unit prices ("Cena/kg:").
     * If no "Cena:" label exists, picks the entity with the smallest value
     * (total price is always less than unit-price × weight).
     */
    private fun selectTotalPrice(
        fullText: String,
        entities: List<MoneyAnnotation>
    ): Pair<String, String> {
        if (entities.size == 1) {
            return formatEntity(entities[0])
        }

        // Find index of "Cena:" that is NOT preceded by "/"  (i.e. not "Cena/kg:")
        val cenaIndex = findTotalPriceLabelIndex(fullText)
        if (cenaIndex >= 0) {
            val entity = entities.firstOrNull { it.start >= cenaIndex }
            if (entity != null) return formatEntity(entity)
        }

        // Fallback: smallest amount (unit price > total on per-kg labels)
        return formatEntity(entities.minByOrNull { it.amount }!!)
    }

    /**
     * Finds the position of "Cena:" (total price label), skipping "Cena/kg:" occurrences.
     */
    private fun findTotalPriceLabelIndex(text: String): Int {
        var idx = 0
        while (idx < text.length) {
            val pos = text.indexOf("cena", idx, ignoreCase = true)
            if (pos < 0) return -1
            // Skip if it's "Cena/kg:" (unit price)
            val tail = text.substring(pos + 4).trimStart()
            if (tail.startsWith(":")) return pos   // "Cena:" ← total price
            idx = pos + 1
        }
        return -1
    }

    private fun formatEntity(entity: MoneyAnnotation): Pair<String, String> {
        val formatted = if (entity.amount == entity.amount.toLong().toDouble()) {
            entity.amount.toLong().toString()
        } else {
            "%.2f".format(entity.amount)
        }
        return Pair(formatted, normalizeCurrency(entity.currency))
    }

    private fun normalizeCurrency(raw: String): String = when {
        raw.contains("грн", ignoreCase = true) || raw.contains("uah", ignoreCase = true) -> "UAH"
        raw.contains("PLN", ignoreCase = true) ||
        raw.contains("zł", ignoreCase = true) ||
        raw.contains("zt", ignoreCase = true) ||
        raw.contains("zl", ignoreCase = true) -> "PLN"
        raw.contains("USD", ignoreCase = true) || raw.contains("$") -> "USD"
        raw.contains("EUR", ignoreCase = true) || raw.contains("€") -> "EUR"
        raw.contains("CZK", ignoreCase = true) || raw.contains("Kč", ignoreCase = true) -> "CZK"
        raw.isNotEmpty() -> raw  // keep unknown as-is
        else -> ""
    }

    // ── Name extraction ──────────────────────────────────────────────────────

    private fun buildProductName(lines: List<String>): String {
        // Join consecutive ALL-CAPS lines (handles multi-line product names like
        // "MORLIŃSKI" + "BOCZEK WĘDZONY" → "MORLIŃSKI BOCZEK WĘDZONY")
        val candidates = mutableListOf<String>()
        val currentGroup = mutableListOf<String>()
        for (line in lines) {
            val t = line.trim()
            if (isAllCapsName(t)) currentGroup.add(t)
            else {
                if (currentGroup.isNotEmpty()) {
                    candidates.add(currentGroup.joinToString(" "))
                    currentGroup.clear()
                }
            }
        }
        if (currentGroup.isNotEmpty()) candidates.add(currentGroup.joinToString(" "))

        val name = candidates.maxByOrNull { it.length } ?: ""
        if (name.isNotEmpty()) return name

        // Fallback: first non-numeric meaningful line
        return lines.map { it.trim() }.firstOrNull { line ->
            line.length > 3 &&
            !line.matches("[0-9.,\\s/:\\-]+".toRegex()) &&
            line.any { it.isLetter() }
        } ?: ""
    }

    private fun isAllCapsName(line: String): Boolean {
        if (line.length < 3) return false
        val letters = line.filter { it.isLetter() }
        if (letters.isEmpty()) return false
        if (letters.length < line.length / 2) return false
        if (line.contains("@")) return false
        return letters.all { it.isUpperCase() }
    }

    // ── Regex backup ─────────────────────────────────────────────────────────

    private fun regexFallback(lines: List<String>, name: String): ScannedProduct {
        val priceRegex = """\d+[.,]\d{2}(?!\d)""".toRegex()
        val unitPricePattern = Regex("""(/kg|per kg|/кг|na kg|cena/kg)""", RegexOption.IGNORE_CASE)
        val weightLine = Regex("""\d+[.,]\d+\s*(kg|g|dkg|ml|l)\b""", RegexOption.IGNORE_CASE)

        var price = ""
        var currency = ""

        for (line in lines) {
            val t = line.trim()
            if (unitPricePattern.containsMatchIn(t)) continue
            if (weightLine.containsMatchIn(t)) continue
            val match = priceRegex.find(t) ?: continue
            val value = match.value.replace(",", ".").toDoubleOrNull() ?: continue
            if (value < 0.5) continue
            price = "%.2f".format(value)
            currency = normalizeCurrency(t)
            break
        }

        return ScannedProduct(name = name, price = price, currency = currency)
    }
}

