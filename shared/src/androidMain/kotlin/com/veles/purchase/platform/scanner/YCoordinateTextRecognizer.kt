package com.veles.purchase.platform.scanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.TimeUnit
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

            // OCR Tiling Strategy: split long receipts into 3 vertical segments with overlap
            val tiles = createTiles(bitmap)
            Log.d(TAG, "Step 2: created ${tiles.size} tiles for processing")

            val allElements = mutableListOf<PositionedLine>()
            var tilesProcessed = 0

            tiles.forEachIndexed { index, tile ->
                val image = InputImage.fromBitmap(tile.bitmap, 0)
                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        if (!continuation.isActive) return@addOnSuccessListener

                        val tileElements = visionText.textBlocks.flatMap { block ->
                            block.lines.flatMap { line ->
                                line.elements.map { element ->
                                    val originalBox = element.boundingBox ?: Rect()
                                    // Map tile coordinates back to original bitmap coordinates
                                    val mappedBox = Rect(
                                        originalBox.left + tile.offsetX,
                                        originalBox.top + tile.offsetY,
                                        originalBox.right + tile.offsetX,
                                        originalBox.bottom + tile.offsetY
                                    )
                                    PositionedLine(text = element.text, box = mappedBox)
                                }
                            }
                        }
                        
                        synchronized(allElements) {
                            allElements.addAll(tileElements)
                            tilesProcessed++
                        }

                        Log.d(TAG, "Tile $index complete: elements=${tileElements.size}")
                        tile.bitmap.recycle()

                        if (tilesProcessed == tiles.size) {
                            val finalElements = deduplicateElements(allElements)
                            Log.d(TAG, "Step 2 complete: total unique elements=${finalElements.size}")
                            val rows = buildRows(finalElements)
                            val stitchedRows = stitchRows(rows)
                            val output = formatRows(stitchedRows)
                            Log.d(TAG, "Step 3 complete: rows=${output.size}\n${output.joinToString("\n")}")
                            continuation.resume(output)
                        }
                    }
                    .addOnFailureListener { e ->
                        if (!continuation.isActive) return@addOnFailureListener
                        Log.e(TAG, "Step 2 failed on tile $index", e)
                        tile.bitmap.recycle()
                        // If one tile fails, we still try to proceed if we have any data
                        synchronized(allElements) {
                            tilesProcessed++
                            if (tilesProcessed == tiles.size) {
                                if (allElements.isEmpty()) continuation.resumeWithException(e)
                                else {
                                    val finalElements = deduplicateElements(allElements)
                                    val rows = buildRows(finalElements)
                                    val stitchedRows = stitchRows(rows)
                                    continuation.resume(formatRows(stitchedRows))
                                }
                            }
                        }
                    }
            }
            
            bitmap.recycle()
        }

    private fun stitchRows(rows: List<List<PositionedLine>>): List<List<PositionedLine>> {
        if (rows.size < 2) return rows
        val result = mutableListOf<MutableList<PositionedLine>>()
        
        for (row in rows) {
            if (row.isEmpty()) continue
            
            val lastRow = result.lastOrNull()
            val isCurrentLineTechnical = isTechnicalLine(row)
            
            if (lastRow != null && isCurrentLineTechnical && !isTechnicalLine(lastRow)) {
                // If last row was a name and current is technical (price/weight), merge them
                lastRow.addAll(row)
            } else {
                result.add(row.toMutableList())
            }
        }
        return result
    }

    private fun isTechnicalLine(row: List<PositionedLine>): Boolean {
        val text = row.joinToString(" ") { it.text }
        // Heuristic: technical lines often contain "x", digits, KG, SZT, or currency symbols
        val hasQuantity = text.contains(Regex("""\d\s?[sS][zZ][tT]""")) || text.contains(" x ", ignoreCase = true)
        val hasWeight = text.contains(Regex("""\d[.,]\d+\s?[kK][gG]"""))
        val hasPrice = text.contains(Regex("""\d+[.,]\d{2}"""))
        
        // If it starts with a number or technical keyword, it's likely a sub-line of a product
        val startsWithTechnical = text.take(3).any { it.isDigit() } || text.startsWith("OPUST", ignoreCase = true)
        
        return hasQuantity || hasWeight || hasPrice || startsWithTechnical
    }

    private data class Tile(val bitmap: Bitmap, val offsetX: Int, val offsetY: Int)

    private fun createTiles(original: Bitmap): List<Tile> {
        val w = original.width
        val h = original.height
        val tiles = mutableListOf<Tile>()

        // For long receipts, we slice vertically
        if (h > w * 1.5) {
            val sliceH = h / 3
            val overlap = sliceH / 4 // 25% overlap
            
            // Top tile
            tiles.add(Tile(Bitmap.createBitmap(original, 0, 0, w, (sliceH + overlap).coerceAtMost(h)), 0, 0))
            // Middle tile
            val midY = sliceH - overlap/2
            tiles.add(Tile(Bitmap.createBitmap(original, 0, midY, w, (sliceH + overlap).coerceAtMost(h - midY)), 0, midY))
            // Bottom tile
            val botY = (2 * sliceH - overlap).coerceAtLeast(0)
            tiles.add(Tile(Bitmap.createBitmap(original, 0, botY, w, h - botY), 0, botY))
        } else {
            // Regular aspect ratio - single tile
            tiles.add(Tile(original.copy(original.config ?: Bitmap.Config.ARGB_8888, true), 0, 0))
        }
        return tiles
    }

    private fun deduplicateElements(elements: List<PositionedLine>): List<PositionedLine> {
        if (elements.isEmpty()) return emptyList()
        // Sort to process in a consistent order
        val sorted = elements.sortedBy { it.box.top }
        val result = mutableListOf<PositionedLine>()

        for (item in sorted) {
            val isDuplicate = result.any { existing ->
                if (existing.text == item.text) {
                    val overlapArea = Rect()
                    if (overlapArea.setIntersect(existing.box, item.box)) {
                        val areaItem = item.box.width() * item.box.height()
                        val areaOverlap = overlapArea.width() * overlapArea.height()
                        // If 70% of the box is already covered by an identical text at a very similar position
                        areaOverlap > (areaItem * 0.7)
                    } else false
                } else false
            }
            if (!isDuplicate) result.add(item)
        }
        return result
    }

    // ── Row grouping ──────────────────────────────────────────────────────────

    private fun buildRows(elements: List<PositionedLine>): List<List<PositionedLine>> {
        if (elements.isEmpty()) return emptyList()

        // Detect if the dominant text flow is horizontal or vertical.
        val avgWidth = elements.map { it.box.width() }.average()
        val avgHeight = elements.map { it.box.height() }.average()
        val isSideways = avgHeight > avgWidth * 1.5

        val sortedElements = if (isSideways) {
            // For sideways (rotated 90 deg clockwise), top of receipt is at the RIGHT (max X).
            // So we group by X (descending).
            elements.sortedByDescending { it.box.centerX() }
        } else {
            elements.sortedBy { it.centerY }
        }

        val yThreshold = (if (isSideways) avgWidth else avgHeight).toInt().coerceAtLeast(8) / 2
        val rows = mutableListOf<MutableList<PositionedLine>>()

        sortedElements.forEach { element ->
            val coord = if (isSideways) element.box.centerX() else element.centerY

            val match = rows.minByOrNull { row ->
                val rowCoord = if (isSideways) row.averageCenterX() else row.averageCenterY()
                abs(rowCoord - coord)
            }?.takeIf { row ->
                val rowCoord = if (isSideways) row.averageCenterX() else row.averageCenterY()
                abs(rowCoord - coord) <= yThreshold
            }

            if (match != null) match.add(element) else rows.add(mutableListOf(element))
        }

        val groupedRows = rows.sortedBy { row ->
            if (isSideways) row.averageCenterX() else row.averageCenterY()
        }

        // Inside each row, ensure horizontal sorting (X for normal, Y for sideways)
        return groupedRows.map { row ->
            if (isSideways) row.sortedByDescending { it.box.centerY() }
            else row.sortedBy { it.box.left }
        }
    }

    private fun List<PositionedLine>.averageCenterX(): Int = sumOf { it.box.centerX() } / size

    // ── Output formatting ─────────────────────────────────────────────────────

    private fun formatRows(rows: List<List<PositionedLine>>): List<String> {
        if (rows.isEmpty()) return emptyList()
        val allElements = rows.flatten()
        val avgWidth = allElements.map { it.box.width() }.average()
        val avgHeight = allElements.map { it.box.height() }.average()
        val isSideways = avgHeight > avgWidth * 1.5

        return rows.mapIndexed { index, row ->
            val sb = StringBuilder()
            if (row.isNotEmpty()) {
                sb.append(row[0].text)
                for (i in 1 until row.size) {
                    val prev = row[i - 1]
                    val curr = row[i]
                    val gap = if (isSideways) abs(curr.box.top - prev.box.bottom) else abs(curr.box.left - prev.box.right)
                    val spaceThreshold = (if (isSideways) prev.box.height() else prev.box.width()) * 0.8
                    
                    if (gap > spaceThreshold) {
                        sb.append(COLUMN_SEPARATOR)
                    } else {
                        sb.append(" ")
                    }
                    sb.append(curr.text)
                }
            }
            val out = sb.toString()
            Log.d(TAG, "Step 3 row[$index]: out='$out'")
            out
        }
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    private data class PositionedLine(val text: String, val box: Rect) {
        val centerY: Int = box.centerY()
    }

    private fun List<PositionedLine>.averageCenterY(): Int = if (isEmpty()) 0 else sumOf { it.centerY } / size
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
