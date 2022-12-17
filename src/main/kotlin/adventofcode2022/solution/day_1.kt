package adventofcode2022.solution

import adventofcode2022.util.batchedList
import adventofcode2022.util.readDay

private const val DAY_NUM = 1

fun main() {
    val inputText = readDay(DAY_NUM)
    println("Day $DAY_NUM")

    println("Part 1 solution: ${solution1(inputText)}")
    println("Part 2 solution: ${solution2(inputText)}")
}

fun solution1(inputText: String): Long? {
    val input = batchedList(inputText.lines().map { it.toLongOrNull() })

    return input.maxOfOrNull { batch -> batch.sumOf { it ?: 0 } }
}

fun solution2(inputText: String): Long? {
    val input = batchedList(inputText.lines().map { it.toLongOrNull() })

    return input.map { batch -> batch.sumOf { it ?: 0 } }.sortedDescending().take(3).sum()
}