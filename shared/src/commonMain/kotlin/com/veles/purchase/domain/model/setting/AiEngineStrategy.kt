package com.veles.purchase.domain.model.setting

import kotlinx.serialization.Serializable

@Serializable
enum class AiEngineStrategy {
    /**
     * Automatic selection:
     * 1. Use Gemini Nano if available (Pixel 9+ / Samsung S25+).
     * 2. Fall back to Groq Cloud if internet is available and API key is set.
     * 3. Fall back to Local Gemma offline model if downloaded.
     * 4. Show AiUnavailable if none of the above.
     */
    AUTO,

    /** Always use Groq Cloud (requires internet, fast, multimodal Llama 4 Scout). */
    GROQ_CLOUD,

    /** Always use Local Gemma (offline, OCR + text model, requires ~1 GB download). */
    LOCAL_DOWNLOAD,

    /** Use Classic OCR + Y-sorting + Text LLM. Fast, low resource, offline. */
    OCR_TEXT_LLM,

    /**
     * Two-pass: ML Kit OCR extracts text locally, then sends plain text to
     * Groq llama-3.3-70b-versatile for structured parsing.
     * Much better accuracy than vision-only Groq on dense receipts.
     * Requires internet. No model download needed.
     */
    OCR_GROQ
}
