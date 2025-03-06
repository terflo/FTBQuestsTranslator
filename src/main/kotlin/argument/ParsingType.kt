package ru.terflo.argument

import java.lang.IllegalArgumentException

enum class ParsingType {

    DESCRIPTION,
    SUBTITLE,
    TITLE,
    HOVER;

    companion object {

        fun of(value: String): ParsingType {
            val preparedValue = value.trim().uppercase()
            entries.forEach {
                if (preparedValue == it.name) {
                    return it
                }
            }
            throw IllegalArgumentException("No such value with name \"$preparedValue\"")
        }

    }

}