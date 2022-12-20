package adventofcode2022.solution

import adventofcode2022.util.readDay
import java.lang.Long.min

fun main() {
    Day10("10").solve()
}

class Day10(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        val cycleValues = mutableMapOf<Int, Long>()
        var cycle = 1
        var regX = 1L

        inputText.lines().forEach {
            when {
                it == "noop" -> {
                    cycleValues[cycle] = regX
                    cycle++
                }

                it.startsWith("addx ") -> {
                    cycleValues[cycle] = regX
                    cycle++

                    println("cycle $cycle: x=$regX")
                    regX += it.substring("addx ".length).toLong()
                    cycleValues[cycle] = regX
                    cycle++
                }
            }
            println("cycle $cycle: x=$regX")
        }

        return cycleValues.filter {
            listOf(
                20,
                60,
                100,
                140,
                180,
                220
            ).contains(it.key)
        }.map { it.key * it.value }
            .sum()
    }

    fun solution2(): Long {
        val cycleValues = mutableMapOf<Int, Int>()
        var cycle = 0
        var regX = 1
        val screen = IntRange(0, 239).associateWith { '.' }.toMap().toMutableMap()

        inputText.lines().forEach {
            when {
                it == "noop" -> {
                    screen.draw(regX, cycle)
                    cycleValues[cycle] = regX
                    cycle++
                }

                it.startsWith("addx ") -> {
                    screen.draw(regX, cycle)
                    cycleValues[cycle] = regX
                    cycle++

                    screen.draw(regX, cycle)
                    regX += it.substring("addx ".length).toInt()
                    cycleValues[cycle] = regX
                    cycle++
                }
            }
        }

        screen.values.map { it.toString() }.joinToString("") { it }
            .chunked(40)
            .forEach {
                println(it)
            }

        return 0
    }

    private fun MutableMap<Int, Char>.draw(pixel: Int, cycle: Int) {
        print("cycle $cycle: x=$pixel (sprite=(${pixel - 1}-${pixel + 1})")

        val cycle2 = cycle % 40
        if (pixel - 1 <= cycle2 && cycle2 <= pixel + 1) {
            this[cycle] = '#'
            print("  DRAW")
        }
        println()
    }
}