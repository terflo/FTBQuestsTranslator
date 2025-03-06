package ru.terflo.translate.libre.dto

internal data class LibreTranslateRequestDto(
    val q: String = "",
    val source: String = "auto",
    val target: String = "ru",
    val format: String = "text",
    val alternatives: Int = 0,
    val api_key: String? = null
)
