package adventofcode2022.solution

import adventofcode2022.util.batchedList
import adventofcode2022.util.readDay

private const val DAY_NUM = 1

fun main() {
    Day1(DAY_NUM.toString()).solve()
}

class Day1(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        val input = batchedList(inputText.lines().map { it.toLongOrNull() })

        return input.maxOfOrNull { batch -> batch.sumOf { it ?: 0 } }!!
    }

    fun solution2(): Long {
        val input = batchedList(inputText.lines().map { it.toLongOrNull() })

        return input.map { batch -> batch.sumOf { it ?: 0 } }.sortedDescending().take(3).sum()
    }
}