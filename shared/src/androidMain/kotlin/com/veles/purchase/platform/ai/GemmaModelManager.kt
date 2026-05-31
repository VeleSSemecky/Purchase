package com.veles.purchase.platform.ai

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.GZIPInputStream

private const val TAG = "GemmaModelManager"

/**
 * Manages downloading and storing the Gemma 3 1B INT4 model.
 *
 * Source: Kaggle — google/gemma-3/TfLite/gemma3-1b-it-int4
 * Requires Kaggle token (KGAT_...) + Gemma licence accepted on the model page.
 * The Kaggle download endpoint returns a ZIP archive which is extracted automatically.
 */
class GemmaModelManager(private val context: Context) {

    private val modelsDir: File
        get() = File(context.filesDir, "models").also { it.mkdirs() }

    val modelFile: File
        get() = File(modelsDir, GemmaModelConfig.MODEL_FILENAME)

    fun isModelDownloaded(): Boolean =
        modelFile.exists() && modelFile.length() > GemmaModelConfig.MODEL_SIZE_BYTES / 2

    fun downloadModel(token: String): Flow<GemmaDownloadState> = channelFlow {
        if (isModelDownloaded()) {
            send(GemmaDownloadState.Downloaded)
            return@channelFlow
        }

        send(GemmaDownloadState.Downloading(0))

        val tmpZip = File(modelsDir, "${GemmaModelConfig.MODEL_FILENAME}.tar.gz.tmp")
        val tmpTask = File(modelsDir, "${GemmaModelConfig.MODEL_FILENAME}.tmp")
        try {
            withContext(Dispatchers.IO) {
                // Step 1: download ZIP from Kaggle
                val connection = openConnection(GemmaModelConfig.KAGGLE_DOWNLOAD_URL, token)
                val totalBytes = connection.contentLengthLong
                    .takeIf { it > 0 } ?: (GemmaModelConfig.MODEL_SIZE_BYTES / 2) // zip is ~half
                var bytesRead = 0L
                var lastPercent = -1

                connection.inputStream.use { input ->
                    tmpZip.outputStream().use { output ->
                        val buffer = ByteArray(128 * 1024)
                        var read: Int
                        while (input.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                            bytesRead += read
                            // Report 0..50% for download phase
                            val percent = ((bytesRead * 50) / totalBytes).toInt().coerceAtMost(50)
                            if (percent != lastPercent) {
                                lastPercent = percent
                                trySend(GemmaDownloadState.Downloading(percent))
                            }
                        }
                    }
                }

                // Step 2: extract .task file from tar.gz
                trySend(GemmaDownloadState.Downloading(55))
                extractTaskFromTarGz(tmpZip, tmpTask)

                tmpTask.renameTo(modelFile)
                tmpZip.delete()
                Log.d(TAG, "Model ready at ${modelFile.absolutePath} (${modelFile.length()} bytes)")
            }
            send(GemmaDownloadState.Downloaded)
        } catch (e: Exception) {
            tmpZip.delete()
            tmpTask.delete()
            Log.e(TAG, "Download failed: ${e.message}")
            send(GemmaDownloadState.Failed(e.message ?: "Download failed"))
        }
    }

    private fun openConnection(urlString: String, token: String): HttpURLConnection {
        var current = urlString
        repeat(5) {
            val conn = (URL(current).openConnection() as HttpURLConnection).apply {
                if (token.isNotBlank()) setRequestProperty("Authorization", "Bearer $token")
                instanceFollowRedirects = false
                connectTimeout = 30_000
                readTimeout = 60_000
                connect()
            }
            return when (conn.responseCode) {
                HttpURLConnection.HTTP_OK -> conn
                HttpURLConnection.HTTP_MOVED_TEMP,
                HttpURLConnection.HTTP_MOVED_PERM,
                307, 308 -> {
                    val location = conn.getHeaderField("Location")
                        ?: throw Exception("Redirect with no Location header")
                    conn.disconnect()
                    current = location
                    return@repeat
                }
                else -> throw Exception("HTTP ${conn.responseCode}: ${conn.responseMessage}")
            }
        }
        throw Exception("Too many redirects for $urlString")
    }

    /**
     * Extracts the first .task file found inside a .tar.gz archive.
     * Uses GZIPInputStream + manual TAR block parsing (no external deps needed).
     */
    private fun extractTaskFromTarGz(tarGzFile: File, outFile: File) {
        GZIPInputStream(tarGzFile.inputStream().buffered()).use { gzip ->
            // TAR format: 512-byte blocks. Header at start of each entry.
            val header = ByteArray(512)
            val buffer = ByteArray(128 * 1024)

            while (true) {
                // Read next 512-byte header block
                val headerRead = gzip.readNBytes(header, 0, 512)
                if (headerRead < 512 || header.all { it == 0.toByte() }) break

                // Extract filename (offset 0, max 100 bytes, null-terminated)
                val nameEnd = header.indexOfFirst { it == 0.toByte() }.takeIf { it >= 0 } ?: 100
                val entryName = String(header, 0, nameEnd, Charsets.US_ASCII).trim('/')

                // Extract file size (offset 124, 12 bytes, octal string)
                val sizeStr = String(header, 124, 12, Charsets.US_ASCII).trim { it <= ' ' || it == '\u0000' }
                val fileSize = if (sizeStr.isBlank()) 0L else sizeStr.toLong(8)

                // Entry type (offset 156): '0' or '\0' = regular file
                val typeFlag = header[156]
                val isRegularFile = typeFlag == '0'.code.toByte() || typeFlag == 0.toByte()

                if (isRegularFile && entryName.endsWith(".task")) {
                    // Extract this entry
                    outFile.outputStream().use { out ->
                        var remaining = fileSize
                        while (remaining > 0) {
                            val toRead = minOf(buffer.size.toLong(), remaining).toInt()
                            val read = gzip.read(buffer, 0, toRead)
                            if (read == -1) break
                            out.write(buffer, 0, read)
                            remaining -= read
                        }
                    }
                    // Skip padding to next 512-byte boundary
                    val paddingBytes = if (fileSize % 512 != 0L) 512 - (fileSize % 512) else 0
                    if (paddingBytes > 0) gzip.skip(paddingBytes)
                    Log.d(TAG, "Extracted $entryName (${fileSize} bytes) → ${outFile.name}")
                    return
                } else {
                    // Skip file data + padding
                    val padded = if (fileSize % 512 != 0L) fileSize + (512 - fileSize % 512) else fileSize
                    gzip.skip(padded)
                }
            }
            throw Exception("No .task file found inside the downloaded tar.gz archive")
        }
    }

    fun deleteModel() {
        modelFile.delete()
        Log.d(TAG, "Model deleted")
    }
}

/** Android implementation of [GemmaModelRepository] backed by [GemmaModelManager]. */
class AndroidGemmaModelRepository(context: Context) : GemmaModelRepository {
    val manager = GemmaModelManager(context)
    private val prefs = context.getSharedPreferences("gemma_prefs", Context.MODE_PRIVATE)

    override fun isModelDownloaded(): Boolean = manager.isModelDownloaded()
    override fun downloadModel(hfToken: String): Flow<GemmaDownloadState> = manager.downloadModel(hfToken)
    override fun deleteModel() = manager.deleteModel()
    override fun savedToken(): String = prefs.getString("kaggle_token", "") ?: ""
    override fun saveToken(token: String) { prefs.edit().putString("kaggle_token", token).apply() }
}
