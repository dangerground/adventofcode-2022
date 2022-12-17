package adventofcode2022.solution

import adventofcode2022.util.readDay

private const val DAY_NUM = 0

fun main() {
    Day0(DAY_NUM.toString()).solve()
}

class Day0(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        return 0
    }

    fun solution2(): Long {
        return 0
    }
}