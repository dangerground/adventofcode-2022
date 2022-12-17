package adventofcode2022.solution

import adventofcode2022.util.readDay

private const val DAY_NUM = 3

fun main() {
    Day3(DAY_NUM.toString()).solve()
}

class Day3(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        return inputText.lines().map {
            val first = it.substring(0, it.length / 2)
            val second = it.substring(it.length / 2)

            val item = findMatching(first, second)

            return@map item.priority()
        }.sum().toLong()
    }

    private fun findMatching(second: String, first: String): String {
        val x = second.replace(Regex("[^$first]+"), "")
        val y = first.replace(Regex("[^$x]"), "")
        return y
    }

    fun solution2(): Long {
        return inputText.lines().chunked(3).map {
            val first = it[0]
            val second = it[1]
            val third = it[2]

            val item = findMatching(first, findMatching(second, third))

            return@map item.priority()
        }.sum().toLong()
    }

    private fun String.priority(): Int {
        return if (this[0].isUpperCase()) {
            this[0].code - 38
        } else {
            this[0].code - 96
        }
    }
}