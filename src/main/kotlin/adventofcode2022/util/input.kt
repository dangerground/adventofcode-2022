package adventofcode2022.util

import java.io.File

fun readDay(day: Int) =
    object {}::class.java.classLoader
        .getResource("input_$day.txt").file
        .let { File(it).readText() }

fun <T> batchedList(lines: List<T>): List<List<T>> {
    val batches = mutableListOf<List<T>>()
    var batch = mutableListOf<T>()
    lines.forEach {
        if (it == null || it.toString().isBlank()) {
            batches.add(batch)
            batch = mutableListOf()
        } else {
            batch.add(it)
        }
    }
    batches.add(batch)

    return batches
}