package description

import ru.terflo.parser.description.DescriptionSnbtTranslator
import ru.terflo.regex.RegexFilter
import translate.libre.LibreTranslateClient
import util.FileUtil
import kotlin.test.Test

class DescriptionSnbtTranslatorTest {

    @Test
    fun translateFileTest() {
        val client = LibreTranslateClient()
        val regexFilter = RegexFilter()
        val content = FileUtil.readFile("/original.snbt")
        val parser = DescriptionSnbtTranslator(client, regexFilter)
        println(content?.let { parser.parse(it) })
    }

}