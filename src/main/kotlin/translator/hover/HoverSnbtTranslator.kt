package ru.terflo.translator.hover

import ru.terflo.regex.RegexFilter
import ru.terflo.translator.block.BlockSnbtTranslator
import translate.TranslateClient

private const val HOVER_TAG = "hover: ["
private const val CLOSE_TAG = ']'

class HoverSnbtTranslator(
    translateClient: TranslateClient,
    regexFilter: RegexFilter,
    sourceLang: String = "en",
    targetLang: String = "ru"
) : BlockSnbtTranslator(
    translateClient,
    regexFilter,
    sourceLang,
    targetLang,
    HOVER_TAG,
    CLOSE_TAG,
    "Hover translation..."
)