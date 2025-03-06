package ru.terflo.regex

class RegexFilter(
    private val regex: Regex = Regex("&[a-zA-z]|&[0-9]")
) {

    fun filter(text: String) : String = text.replace(regex, "")

}