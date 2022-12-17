package adventofcode2022.solution

import adventofcode2022.util.readDay
import java.lang.RuntimeException

private const val DAY_NUM = 2

fun main() {
    Day2(DAY_NUM.toString()).solve()
}

class Day2(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        return inputText.lines().map {
            val round = it.split(" ")
            val opponent = Sign.ofFirst(round.first())
            val me = Sign.ofSecond(round.last())

            return@map opponent.versus(me) + me.points
        }.sum()
    }

    fun solution2(): Long {

        return inputText.lines().map {
            val round = it.split(" ")
            val opponent = Sign.ofFirst(round.first())
            val me = when (round.last()) {
                "X" -> opponent.winsAgainst()
                "Y" -> opponent
                "Z" -> opponent.loseAgainst()
                else -> throw RuntimeException("not happening")
            }

            return@map opponent.versus(me) + me.points
        }.sum()
    }
}

enum class Sign(val points: Long) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun ofFirst(sign: String) = when (sign) {
            "A" -> ROCK
            "B" -> PAPER
            "C" -> SCISSORS
            else -> throw RuntimeException("not happening")
        }

        fun ofSecond(sign: String) = when (sign) {
            "X" -> ROCK
            "Y" -> PAPER
            "Z" -> SCISSORS
            else -> throw RuntimeException("not happening")
        }
    }

    private fun loses(against: Sign) = this == against.winsAgainst()
    fun winsAgainst() = when (this) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
    }

    fun loseAgainst() = when (this) {
        ROCK -> PAPER
        PAPER -> SCISSORS
        SCISSORS -> ROCK
    }

    fun versus(against: Sign) = if (this == against) {
        3
    } else if (this.loses(against)) {
        6
    } else {
        0
    }
}