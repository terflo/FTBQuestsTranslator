package ru.terflo.file

import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

private val DEFAULT_CHARSET: Charset = StandardCharsets.UTF_8;

class FileUtil {

    companion object {

        fun readFile(path: String): String = File(path).readText(DEFAULT_CHARSET)

        fun writeFile(path: String, text: String) = File(path).writeText(text)

    }

}