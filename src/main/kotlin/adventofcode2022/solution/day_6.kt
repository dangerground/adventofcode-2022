package adventofcode2022.solution

import adventofcode2022.util.readDay

private const val DAY_NUM = 6

fun main() {
    Day6(DAY_NUM.toString()).solve()
}

class Day6(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        return sequenzeStart(4)
    }

    private fun sequenzeStart(length: Int) =
        inputText.windowed(length).indexOfFirst { it.toSet().size == length }.toLong() + length

    fun solution2(): Long {
        return sequenzeStart(14)
    }
}