package ru.terflo.task

import ru.terflo.argument.ArgumentConfig
import ru.terflo.argument.ParsingType
import ru.terflo.file.FileUtil
import ru.terflo.parser.SnbtTranslator
import ru.terflo.translator.description.DescriptionSnbtTranslator
import ru.terflo.translator.subtitle.SubtitleSnbtTranslator
import ru.terflo.translator.title.TitleSnbtTranslator
import ru.terflo.regex.RegexFilter
import ru.terflo.translator.hover.HoverSnbtTranslator
import translate.TranslateClient
import translate.libre.LibreTranslateClient

class TaskExecutor(private val config: ArgumentConfig) {

    private val regexFilter: RegexFilter = RegexFilter()
    private val translateClient: TranslateClient
    private val snbtTranslators: Collection<SnbtTranslator>

    init {
        this.translateClient = initTranslateClient()
        this.snbtTranslators = initSnbtTranslators()
    }

    fun translate() {
        val text = FileUtil.readFile(config.originalFileName)
        val result = process(text)
        FileUtil.writeFile(config.resultFileName, result)
        println("Completed")
    }

    private fun process(text: String): String {
        var result = text
        this.snbtTranslators.forEach {
            result = it.parse(result)
        }
        return result
    }

    private fun initTranslateClient(): TranslateClient = LibreTranslateClient(
        apiUrl = config.apiUrl,
        apiKey = config.apiKey,
    )

    private fun initSnbtTranslators(): Collection<SnbtTranslator> =
        config.parsingType
            .map { initTranslator(it) }
            .toSet()

    private fun initTranslator(parsingType: ParsingType): SnbtTranslator =
        when (parsingType) {
            ParsingType.DESCRIPTION -> DescriptionSnbtTranslator(
                translateClient,
                regexFilter,
                config.sourceLang,
                config.targetLang
            )

            ParsingType.TITLE -> TitleSnbtTranslator(
                translateClient,
                regexFilter,
                config.sourceLang,
                config.targetLang
            )

            ParsingType.HOVER -> HoverSnbtTranslator(
                translateClient,
                regexFilter,
                config.sourceLang,
                config.targetLang
            )

            ParsingType.SUBTITLE -> SubtitleSnbtTranslator(
                translateClient,
                regexFilter,
                config.sourceLang,
                config.targetLang
            )
        }


}