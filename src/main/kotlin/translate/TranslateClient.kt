package translate

interface TranslateClient {

    fun translate(text: String, sourceLang: String, targetLang: String): String

}