package ru.terflo.translator.description

import ru.terflo.regex.RegexFilter
import ru.terflo.translator.block.BlockSnbtTranslator
import translate.TranslateClient

private const val DESCRIPTION_TAG = "description: ["
private const val CLOSE_TAG = ']'

class DescriptionSnbtTranslator(
    translateClient: TranslateClient,
    regexFilter: RegexFilter,
    sourceLang: String = "en",
    targetLang: String = "ru"
) : BlockSnbtTranslator(
    translateClient,
    regexFilter,
    sourceLang,
    targetLang,
    DESCRIPTION_TAG,
    CLOSE_TAG,
    "Description translation..."
)