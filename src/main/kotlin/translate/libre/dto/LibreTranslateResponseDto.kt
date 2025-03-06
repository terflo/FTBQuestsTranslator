package ru.terflo.translate.libre.dto

internal data class LibreTranslateResponseDto(
    val alternatives: List<String> = listOf(),
    val translatedText: String = "",
    val error: String? = null
)
