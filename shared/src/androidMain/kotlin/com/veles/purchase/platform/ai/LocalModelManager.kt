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

private const val TAG = "LocalModelManager"

class LocalModelManager(
    private val context: Context,
    private val kaggleApiKey: String
) : LocalModelDownloader {

    private val modelsDir: File
        get() = File(context.filesDir, "models").also { it.mkdirs() }

    val modelFile: File
        get() = File(modelsDir, GemmaModelConfig.MODEL_FILENAME)

    fun isModelDownloaded(): Boolean =
        modelFile.exists() && modelFile.length() > GemmaModelConfig.MODEL_SIZE_BYTES / 2

    /**
     * Downloads the model from Kaggle and streams-extracts the .task file directly,
     * without saving the full tar.gz archive to disk first.
     * Peak disk usage = size of extracted .task file only (~2 GB), not tar.gz + .task (~4 GB).
     */
    override fun downloadModel(): Flow<GemmaDownloadState> = channelFlow {
        if (isModelDownloaded()) {
            send(GemmaDownloadState.Downloaded)
            return@channelFlow
        }

        send(GemmaDownloadState.Downloading(0))

        val tmpTask = File(modelsDir, "${GemmaModelConfig.MODEL_FILENAME}.tmp")
        Log.d(TAG, "Kaggle token present: ${kaggleApiKey.isNotEmpty()}, prefix: ${kaggleApiKey.take(8)}")
        try {
            withContext(Dispatchers.IO) {
                val connection = openConnection(
                    GemmaModelConfig.KAGGLE_DOWNLOAD_URL,
                    kaggleApiKey
                )
                val totalBytes = connection.contentLengthLong
                    .takeIf { it > 0 } ?: GemmaModelConfig.MODEL_SIZE_BYTES
                var bytesRead = 0L
                var lastPercent = -1

                // Stream: HTTP → GZIP → TAR → .task file (no intermediate tar.gz on disk)
                connection.inputStream.use { raw ->
                    val countingStream = object : java.io.FilterInputStream(raw) {
                        override fun read(b: ByteArray, off: Int, len: Int): Int =
                            super.read(b, off, len).also { n ->
                                if (n > 0) {
                                    bytesRead += n
                                    val percent = ((bytesRead * 95) / totalBytes).toInt().coerceIn(0, 95)
                                    if (percent != lastPercent) {
                                        lastPercent = percent
                                        trySend(GemmaDownloadState.Downloading(percent))
                                    }
                                }
                            }
                    }
                    extractTaskFromTarGzStream(countingStream, tmpTask)
                }

                tmpTask.renameTo(modelFile)
                Log.d(TAG, "Model ready at ${modelFile.absolutePath} (${modelFile.length()} bytes)")
            }
            send(GemmaDownloadState.Downloaded)
        } catch (e: Exception) {
            tmpTask.delete()
            Log.e(TAG, "Download failed: ${e.message}", e)
            send(GemmaDownloadState.Failed(e.message ?: "Download failed"))
        }
    }

    fun deleteModel() {
        modelFile.delete()
        Log.d(TAG, "Model deleted")
    }

    private fun openConnection(urlString: String, apiKey: String): HttpURLConnection {
        var location = urlString
        var isKaggleUrl = true
        repeat(10) {
            Log.d(TAG, "Connecting to: ${location.take(120)} (kaggle=$isKaggleUrl)")
            val conn = URL(location).openConnection() as HttpURLConnection
            // Only send auth on Kaggle URLs — GCS signed-URL redirects must NOT have Authorization header
            if (isKaggleUrl) {
                conn.setRequestProperty("Authorization", "Bearer $apiKey")
            }
            conn.instanceFollowRedirects = false
            conn.connect()
            Log.d(TAG, "Response: ${conn.responseCode}")
            when (conn.responseCode) {
                HttpURLConnection.HTTP_OK -> return conn
                HttpURLConnection.HTTP_MOVED_TEMP,
                HttpURLConnection.HTTP_MOVED_PERM,
                307, 308 -> {
                    location = conn.getHeaderField("Location") ?: throw Exception("Redirect with no Location header")
                    isKaggleUrl = location.contains("kaggle.com")
                }
                else -> throw Exception("HTTP ${conn.responseCode}: ${conn.responseMessage} [url=${location.take(120)}]")
            }
        }
        throw Exception("Too many redirects for $urlString")
    }

    private fun extractTaskFromTarGzStream(rawStream: java.io.InputStream, outFile: File) {
        GZIPInputStream(rawStream.buffered()).use { gzip ->
            val header = ByteArray(512)
            val buffer = ByteArray(128 * 1024)

            while (true) {
                val headerRead = gzip.readNBytes(header, 0, 512)
                if (headerRead < 512 || header.all { it == 0.toByte() }) break

                val nameEnd = header.indexOfFirst { it == 0.toByte() }.takeIf { it >= 0 } ?: 100
                val entryName = String(header, 0, nameEnd, Charsets.US_ASCII).trim('/')
                val sizeStr = String(header, 124, 12, Charsets.US_ASCII).trim { it <= ' ' || it == '\u0000' }
                val fileSize = if (sizeStr.isBlank()) 0L else sizeStr.toLong(8)
                val typeFlag = header[156]
                val isRegularFile = typeFlag == '0'.code.toByte() || typeFlag == 0.toByte()

                if (isRegularFile && entryName.endsWith(".task")) {
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
                    val paddingBytes = if (fileSize % 512 != 0L) 512 - (fileSize % 512) else 0
                    if (paddingBytes > 0) gzip.skip(paddingBytes)
                    Log.d(TAG, "Extracted $entryName → ${outFile.name}")
                    return
                } else {
                    val padded = if (fileSize % 512 != 0L) fileSize + (512 - fileSize % 512) else fileSize
                    gzip.skip(padded)
                }
            }
            throw Exception("No .task file found in archive")
        }
    }
}
