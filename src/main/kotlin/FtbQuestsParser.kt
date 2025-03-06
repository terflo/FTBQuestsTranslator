package ru.terflo

import ru.terflo.argument.ArgumentParser
import ru.terflo.task.TaskExecutor

fun main(args: Array<String>) {
    val arguments = ArgumentParser.parse(args)
    val taskExecutor = TaskExecutor(arguments)
    taskExecutor.translate()
}