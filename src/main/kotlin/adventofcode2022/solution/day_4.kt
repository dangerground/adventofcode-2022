package adventofcode2022.solution

import adventofcode2022.util.readDay

private const val DAY_NUM = 4

fun main() {
    Day4(DAY_NUM.toString()).solve()
}

class Day4(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        return inputText.lines().map {
            it.split(",").let {
                it[0].toIntRange() to it[1].toIntRange()
            }
        }
            .count { it.first.contains(it.second) || it.second.contains(it.first) }
            .toLong()
    }

    private fun String.toIntRange() = this.split("-")
        .let {
            IntRange(it[0].toInt(), it[1].toInt())
        }

    private fun IntRange.contains(range: IntRange) = this.contains(range.first) && this.contains(range.last)
    private fun IntRange.overlaps(range: IntRange) = this.contains(range.first) || this.contains(range.last)

    fun solution2(): Long {
        return inputText.lines().map {
            it.split(",").let {
                it[0].toIntRange() to it[1].toIntRange()
            }
        }
            .count { it.first.overlaps(it.second) || it.second.overlaps(it.first) }
            .toLong()
    }
}