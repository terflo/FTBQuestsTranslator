package ru.terflo.argument

import java.lang.IllegalArgumentException
import java.net.URI

data class ArgumentConfig(
    val originalFileName: String,
    val resultFileName: String,
    val sourceLang: String,
    val targetLang: String,
    val apiUrl: URI,
    val apiKey: String?,
    val parsingType: Set<ParsingType>
) {

    class ArgumentConfigBuilder {

        private var originalFileName: String? = null
        private var resultFileName: String = "translated.snbt"
        private var sourceLang: String = "en"
        private var targetLang: String = "ru"
        private var apiUrl: URI = URI("http://192.168.0.199:5000/translate")
        private var apiKey: String? = null
        private var parsingType: Set<ParsingType> = setOf(ParsingType.DESCRIPTION, ParsingType.HOVER, ParsingType.TITLE, ParsingType.SUBTITLE)

        fun originalFileName(originalFileName: String): ArgumentConfigBuilder {
            this.originalFileName = originalFileName
            return this
        }

        fun resultFileName(resultFileName: String): ArgumentConfigBuilder {
            this.resultFileName = resultFileName
            return this
        }

        fun sourceLang(sourceLang: String): ArgumentConfigBuilder {
            this.sourceLang = sourceLang
            return this
        }

        fun targetLang(targetLang: String): ArgumentConfigBuilder {
            this.targetLang = targetLang
            return this
        }

        fun apiUrl(apiUrl: URI): ArgumentConfigBuilder {
            this.apiUrl = apiUrl
            return this
        }

        fun apiKey(apiKey: String?): ArgumentConfigBuilder {
            this.apiKey = apiKey
            return this
        }

        fun parsingType(parsingType: Set<ParsingType>): ArgumentConfigBuilder {
            this.parsingType = parsingType
            return this
        }

        fun build(): ArgumentConfig {
            if (this.originalFileName == null) {
                throw IllegalArgumentException("Original file name argument must be specified")
            }
            return ArgumentConfig(originalFileName!!, resultFileName, sourceLang, targetLang, apiUrl, apiKey,
                parsingType)
        }


    }

    companion object {
        const val ORIGINAL_FILE_NAME = "file"
        const val RESULT_FILE_NAME = "result"
        const val SOURCE_LANG = "source"
        const val TARGET_LANG = "target"
        const val API_URL = "url"
        const val API_KEY = "key"
        const val PARSING_TYPE = "type"
    }

}
