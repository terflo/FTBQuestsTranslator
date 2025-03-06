package ru.terflo.translator.subtitle

import me.tongfei.progressbar.ProgressBar
import ru.terflo.parser.SnbtTranslator
import ru.terflo.regex.RegexFilter
import translate.TranslateClient

private const val SUBTITLE_TAG = "subtitle: \""

class SubtitleSnbtTranslator(
    private val translateClient: TranslateClient,
    private val regexFilter: RegexFilter,
    private val sourceLang: String = "en",
    private val targetLang: String = "ru"
) : SnbtTranslator {

    override fun parse(text: String): String {
        return process(text)
    }

    private fun process(text: String): String {

        val linesForTranslate = text.split("\n")
        val totalLines = linesForTranslate.size.toLong()

        ProgressBar("Subtitle translating...", totalLines).use { ps ->
            return linesForTranslate
                .asSequence()
                .map {
                    ps.step()
                    if (it.contains(SUBTITLE_TAG)) {
                        val cuttingText = it.substringAfter("\"").substringBefore("\"")
                        val textAfterFilter = regexFilter.filter(cuttingText)
                        val translatedText = translateClient.translate(textAfterFilter, sourceLang, targetLang)
                        return@map it.replace(cuttingText, translatedText)
                    }
                    return@map it
                }
                .joinToString("\n")

        }
    }

}