package util

import java.nio.charset.StandardCharsets

private val DEFAULT_CHARSET = StandardCharsets.UTF_8

class FileUtil {

    companion object {
        fun readFile(path: String): String? = FileUtil::class.java.getResource(path)?.readText(DEFAULT_CHARSET)

    }

}