package ru.terflo.argument

import java.net.URI

private const val ARGUMENT_DETERMINANT = '='
private const val ARGUMENT_PREFIX = "--"
private const val HTTP_PREFIX = "http://"
private const val HTTPS_PREFIX = "https://"

private val PARSING_TYPES = ParsingType.entries.map { it.name.lowercase() }.toSet()

class ArgumentParser {

    companion object {

        fun parse(arguments: Array<String>): ArgumentConfig {
            val argumentMap = transformArguments(arguments)
            return buildConfig(argumentMap)
        }

        private fun transformArguments(arguments: Array<String>): Map<String, String> = arguments.asList()
            .asSequence()
            .filter { it.startsWith(ARGUMENT_PREFIX) }
            .map { it.replace(ARGUMENT_PREFIX, "") }
            .map { it.split(ARGUMENT_DETERMINANT) }
            .filter { it.size == 2 }
            .associate { it[0] to it[1] }

        private fun buildConfig(arguments: Map<String, String>): ArgumentConfig {
            val builder = ArgumentConfig.ArgumentConfigBuilder()
            arguments.forEach {
                val argName = it.key
                val argValue = it.value
                setupField(builder, argName, argValue)
            }
            return builder.build()
        }

        private fun setupField(builder: ArgumentConfig.ArgumentConfigBuilder, argName: String, argValue: String) {
            when(argName) {
                ArgumentConfig.ORIGINAL_FILE_NAME -> builder.originalFileName(argValue)
                ArgumentConfig.RESULT_FILE_NAME -> builder.resultFileName(argValue)
                ArgumentConfig.SOURCE_LANG -> builder.sourceLang(argValue.lowercase())
                ArgumentConfig.TARGET_LANG -> builder.targetLang(argValue.lowercase())
                ArgumentConfig.API_URL -> builder.apiUrl(mapApiUrl(argValue))
                ArgumentConfig.API_KEY -> builder.apiKey(argValue)
                ArgumentConfig.PARSING_TYPE -> builder.parsingType(mapParsingTypes(argValue))
            }
        }

        private fun mapParsingTypes(parsingType: String): Set<ParsingType> {
            return parsingType.split(",")
                .filter { PARSING_TYPES.contains(it) }
                .map { ParsingType.of(it) }
                .toSet()
        }

        private fun mapApiUrl(url: String): URI {
            return if (url.startsWith(HTTP_PREFIX, true) || url.startsWith(HTTPS_PREFIX, true)) {
                URI(url)
            } else {
                URI("http://$url")
            }
        }
    }

}