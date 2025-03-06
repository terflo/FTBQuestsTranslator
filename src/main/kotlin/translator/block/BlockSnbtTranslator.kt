package ru.terflo.translator.block

import me.tongfei.progressbar.ProgressBar
import ru.terflo.parser.SnbtTranslator
import ru.terflo.regex.RegexFilter
import translate.TranslateClient
import java.lang.StringBuilder

abstract class BlockSnbtTranslator(
    private val translateClient: TranslateClient,
    private val regexFilter: RegexFilter,
    private val sourceLang: String = "en",
    private val targetLang: String = "ru",
    private val openTag: String,
    private val closeTag: Char,
    private val progressBarDescription: String
) : SnbtTranslator {

    private val textBuilder = StringBuilder()
    private val translatedTextBuilder = StringBuilder()

    private var currentIndex = 0
    private var isSeek = false
    private var isOpen = false

    override fun parse(text: String): String {
        process(text)
        return complete()
    }

    private fun process(text: String) {

        val textLength = text.length.toLong()
        ProgressBar(progressBarDescription, textLength).use { ps ->

            text.forEach {
                determineState(it)
                ps.stepTo(textBuilder.length.toLong())
            }

            ps.stepTo(textLength)

        }

    }

    private fun prepareDescription(text: String): String {
        var result = text
        text.split("\"\"")
            .map { it.trim() }
            .flatMap { it.split("\n") }
            .forEach {
                val textAfterCutting = it.substringAfter("\"").substringBefore("\"")
                val textForTranslate = regexFilter.filter(textAfterCutting)
                if (textForTranslate.isNotBlank()) {
                    val translatedText = translateClient.translate(textForTranslate, sourceLang, targetLang)
                    result = result.replace(it, "\"${translatedText}\"")
                } else {
                    result = it
                }
            }
        return result
    }

    private fun determineState(it: Char) {

        if (it == openTag[0]) {
            isSeek = true
        }

        if (isSeek) {
            if (openTag[currentIndex] == it) {
                currentIndex++
                if (openTag.last() == it) {
                    currentIndex = 0
                    isSeek = false
                    isOpen = true
                }
            } else {
                currentIndex = 0
                isSeek = false
            }
        }

        if (isOpen && openTag.last() != it && closeTag != it) {
            translatedTextBuilder.append(it)
        }

        if (!isOpen) {
            textBuilder.append(it)
        }

        if (isOpen && it == closeTag) {
            isOpen = false
            isSeek = false
            val translatedStr = prepareDescription(translatedTextBuilder.toString())
            textBuilder.append("[$translatedStr]")
            translatedTextBuilder.setLength(0)
        }

    }

    private fun complete(): String {
        val result = textBuilder.toString()
        reset()
        return result
    }

    private fun reset() {
        isOpen = false
        isSeek = false
        currentIndex = 0
        textBuilder.setLength(0)
        translatedTextBuilder.setLength(0)
    }

}