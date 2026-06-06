package com.veles.purchase.platform.scanner

import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.abs
import kotlin.math.max

private const val TAG = "YCoordinateOCR"

/**
 * Separator used between OCR columns in a reconstructed row.
 *
 * Each output string from [YCoordinateTextRecognizer] looks like:
 *   "ProductName | UnitPrice | Qty | LineTotal"
 *   "Header Col1 | Header Col2 | ..."
 *   "SingleColumnText"
 *
 * Callers (LLM prompt or [StructuredRowParser]) receive the full column matrix
 * and make their own semantic decisions about which column is name / price / qty.
 * No language-specific classification is done here.
 */
const val COLUMN_SEPARATOR = " | "

/**
 * ML Kit OCR recognizer that rebuilds visual receipt rows by vertical (Y) position
 * and preserves horizontal (X) column order.
 *
 * Output: one string per visual row, columns separated by [COLUMN_SEPARATOR].
 * The number of pipe-separated segments reflects the actual columns on the receipt.
 * No price detection, no keyword matching — pure spatial reconstruction.
 */
class YCoordinateTextRecognizer : TextRecognizer {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override suspend fun recognizeText(imageBytes: ByteArray): List<String> =
        suspendCancellableCoroutine { continuation ->
            Log.d(TAG, "Step 1: decode image bytes=${imageBytes.size}")
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            if (bitmap == null) {
                Log.w(TAG, "Step 1 failed: BitmapFactory returned null")
                continuation.resume(emptyList())
                return@suspendCancellableCoroutine
            }
            Log.d(TAG, "Step 1 complete: bitmap=${bitmap.width}x${bitmap.height}")

            val image = InputImage.fromBitmap(bitmap, 0)
            Log.d(TAG, "Step 2: start ML Kit text recognition")
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    if (!continuation.isActive) return@addOnSuccessListener
                    val allLines = visionText.textBlocks.flatMap { it.lines }
                    Log.d(
                        TAG,
                        "Step 2 complete: blocks=${visionText.textBlocks.size}, rawLines=${allLines.size}"
                    )
                    logRawLines(allLines)

                    val rows = buildRows(allLines)
                    val output = formatRows(rows)
                    Log.d(TAG, "Step 3 complete: rows=${output.size}\n${output.joinToString("\n")}")
                    continuation.resume(output)
                }
                .addOnFailureListener { e ->
                    if (!continuation.isActive) return@addOnFailureListener
                    Log.e(TAG, "Step 2 failed: ML Kit text recognition error", e)
                    continuation.resumeWithException(e)
                }
        }

    // ── Row grouping ──────────────────────────────────────────────────────────

    private fun buildRows(lines: List<Text.Line>): List<List<PositionedLine>> {
        val positionedLines = lines.mapNotNull { line ->
            val box = line.boundingBox ?: return@mapNotNull null
            val text = line.text.trim()
            if (text.isBlank()) return@mapNotNull null
            PositionedLine(text = text, box = box)
        }
        if (positionedLines.isEmpty()) return emptyList()

        val averageHeight = positionedLines.map { it.box.height() }.average().toInt().coerceAtLeast(1)
        val yThreshold = max(8, averageHeight / 2)
        val rows = mutableListOf<MutableList<PositionedLine>>()

        positionedLines
            .sortedWith(compareBy<PositionedLine> { it.centerY }.thenBy { it.box.left })
            .forEach { line ->
                val match = rows.minByOrNull { row -> abs(row.averageCenterY() - line.centerY) }
                    ?.takeIf { row -> abs(row.averageCenterY() - line.centerY) <= yThreshold }

                if (match != null) match.add(line) else rows.add(mutableListOf(line))
            }

        return rows.sortedBy { it.averageCenterY() }
    }

    // ── Output formatting ─────────────────────────────────────────────────────

    /**
     * Converts grouped rows into pipe-separated column strings.
     *
     * Each [PositionedLine] in a row corresponds to one visual column (a [Text.Line]
     * from ML Kit). Columns are ordered left-to-right by bounding-box X position.
     *
     * Example output rows:
     *   "CocaCola 0.2 | €3.00 | 1 | €3.00"
     *   "New York Sour | €8.00 | 2 | €16.00"
     *   "GREENTOUCH UA SL"
     *   "Parcial | €140.50"
     */
    private fun formatRows(rows: List<List<PositionedLine>>): List<String> =
        rows.mapIndexed { index, row ->
            val cols = row.sortedBy { it.box.left }
            val out = cols.joinToString(COLUMN_SEPARATOR) { it.text }
            Log.d(TAG, "Step 3 row[$index]: y=${row.averageCenterY()}, cols=${cols.size}, out='$out'")
            out
        }

    // ── Utilities ─────────────────────────────────────────────────────────────

    private fun logRawLines(lines: List<Text.Line>) {
        lines.forEachIndexed { i, line ->
            Log.d(TAG, "Step 2 rawLine[$i]: box=${line.boundingBox}, text='${line.text}'")
        }
    }

    private data class PositionedLine(val text: String, val box: Rect) {
        val centerY: Int = box.centerY()
    }

    private fun List<PositionedLine>.averageCenterY(): Int = sumOf { it.centerY } / size
}

// ── Structural receipt parser (no language knowledge required) ────────────────

/**
 * Parses structured OCR rows (output of [YCoordinateTextRecognizer]) into
 * receipt items and grand total using **only positional/structural heuristics**:
 *
 * - Rows with ≥ 3 columns → item rows (NAME | ... | LINE_TOTAL)
 *   The **last** column is taken as the line total.
 * - Rows with exactly 2 columns (NAME | AMOUNT) → summary candidates.
 *
 * Grand total is identified as the summary-row amount closest to the sum of item prices.
 *
 * For simple 2-column-only receipts (NAME | PRICE), a sum-matching heuristic is used:
 * the row whose price ≈ sum of all other prices is the grand total.
 *
 * No language keywords. Works for any receipt language.
 * Returns null if fewer than 1 item is found (caller should fall back to LLM).
 */
object StructuredRowParser {

    private val CURRENCY_MAP = mapOf('€' to "EUR", '$' to "USD", '£' to "GBP", '₴' to "UAH")

    // Matches: optional currency symbol + 1-6 digits + comma/dot + 1-2 digits
    private val priceRegex = Regex("""[€$£₴]?\s*([0-9]{1,6}[.,][0-9]{1,2})""")

    fun parse(rows: List<String>): StructuredParseResult? {
        // Rows with ≥ 3 columns: likely item rows (NAME | unit_price | qty | LINE_TOTAL)
        val multiColRows = mutableListOf<Pair<String, Double>>()
        // Rows with exactly 2 columns: (NAME | AMOUNT) — summary or simple item
        val twoColRows = mutableListOf<Pair<String, Double>>()
        var currency = ""

        for (row in rows) {
            val cols = row.split(COLUMN_SEPARATOR).map { it.trim() }.filter { it.isNotBlank() }
            if (cols.size < 2) continue

            val name = cols.first()
            // Skip rows whose first column has no letters (pure numbers, separators, etc.)
            if (!name.any { it.isLetter() }) continue

            val lastPrice = extractPrice(cols.last()) ?: continue

            if (currency.isEmpty()) {
                for (col in cols) {
                    val sym = col.firstOrNull { it in CURRENCY_MAP }
                    if (sym != null) { currency = CURRENCY_MAP[sym] ?: ""; break }
                }
            }

            if (cols.size >= 3) multiColRows.add(name to lastPrice)
            else twoColRows.add(name to lastPrice)
        }

        Log.d("StructuredParser", "multiColRows=${multiColRows.size}, twoColRows=${twoColRows.size}, currency='$currency'")

        return when {
            // Case A: multi-column rows exist → those are items; 2-col rows are summaries
            multiColRows.isNotEmpty() -> {
                val items = multiColRows.map { StructuredItem(it.first, it.second) }
                val itemSum = items.sumOf { it.price }
                val grandTotal = twoColRows.minByOrNull { abs(it.second - itemSum) }?.second
                Log.d("StructuredParser", "Case A: items=${items.size}, itemSum=$itemSum, grandTotal=$grandTotal")
                StructuredParseResult(items = items, total = grandTotal, currency = currency)
            }

            // Case B: only 2-column rows → use sum-matching to find grand total
            twoColRows.size >= 2 -> {
                val sorted = twoColRows.sortedByDescending { it.second }
                val largest = sorted.first()
                val rest = sorted.drop(1)
                val restSum = rest.sumOf { it.second }

                // If largest ≈ sum of the rest (within 10%), treat it as grand total
                val isTotal = restSum > 0 && abs(largest.second - restSum) / restSum < 0.10
                val grandTotal = if (isTotal) largest.second else null
                val itemsList = if (isTotal) rest else twoColRows
                Log.d("StructuredParser", "Case B: isTotal=$isTotal, grandTotal=$grandTotal, items=${itemsList.size}")
                StructuredParseResult(
                    items = itemsList.map { StructuredItem(it.first, it.second) },
                    total = grandTotal,
                    currency = currency
                )
            }

            else -> null
        }.takeIf { it != null && it.items.isNotEmpty() }
    }

    private fun extractPrice(text: String): Double? =
        priceRegex.find(text.trim())?.groupValues?.getOrNull(1)
            ?.replace(',', '.')?.toDoubleOrNull()

    data class StructuredItem(val name: String, val price: Double)
    data class StructuredParseResult(
        val items: List<StructuredItem>,
        val total: Double?,
        val currency: String
    )
}
