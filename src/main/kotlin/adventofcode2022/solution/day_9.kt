package adventofcode2022.solution

import adventofcode2022.util.readDay
import kotlin.math.abs

fun main() {
    Day9("9").solve()
}

class Day9(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        val head = Knot(0, 4)
        val tail = Knot(0, 4)
        val visits = VisitedMap()

        inputText.lines().forEach {
            println()
            println(it)
            when (it.direction()) {
                "D" -> repeat(it.amount()) {
                    head.down()
                    tail.follow(head)
                    visits.track(tail)
                    visits.debug(head, listOf(tail))
                }

                "U" -> repeat(it.amount()) {
                    head.up()
                    tail.follow(head)
                    visits.track(tail)
                    visits.debug(head, listOf(tail))
                }

                "L" -> repeat(it.amount()) {
                    head.left()
                    tail.follow(head)
                    visits.track(tail)
                    visits.debug(head, listOf(tail))
                }

                "R" -> repeat(it.amount()) {
                    head.right()
                    tail.follow(head)
                    visits.track(tail)
                    visits.debug(head, listOf(tail))
                }
            }
        }

        return visits.map { it.value.size }.sum().toLong()
    }

    private fun String.direction() = substring(0, 1)
    private fun String.amount() = substring(2).toInt()

    fun solution2(): Long {
        val knots = listOf(Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot())
//        val knots = listOf(Knot(), Knot())
        val visits = VisitedMap()

        inputText.lines().forEach {
            println()
            println(it)
            when (it.direction()) {
                "D" -> repeat(it.amount()) {
                    knots.first().down()
                    knots.filterIndexed { index, _ -> index > 0 }
                        .forEachIndexed { index, knot -> knot.follow(knots[index]) }
                    visits.track(knots.last())
                }

                "U" -> repeat(it.amount()) {
                    knots.first().up()
                    knots.filterIndexed { index, _ -> index > 0 }
                        .forEachIndexed { index, knot -> knot.follow(knots[index]) }
                    visits.track(knots.last())
                }

                "L" -> repeat(it.amount()) {
                    knots.first().left()
                    knots.filterIndexed { index, _ -> index > 0 }
                        .forEachIndexed { index, knot -> knot.follow(knots[index]) }
                    visits.track(knots.last())
                }

                "R" -> repeat(it.amount()) {
                    knots.first().right()
                    knots.filterIndexed { index, _ -> index > 0 }
                        .forEachIndexed { index, knot -> knot.follow(knots[index]) }
                    visits.track(knots.last())
                    visits.debug(knots.first(), knots.takeLast(9))
                }
            }
        }

        return visits.map { it.value.size }.sum().toLong()
    }
}

typealias VisitedMap = HashMap<Int, MutableMap<Int, Boolean>>

fun VisitedMap.track(knot: Knot) {
    putIfAbsent(knot.y, mutableMapOf())
    get(knot.y)!![knot.x] = true
}

fun VisitedMap.debug(head: Knot, knots: List<Knot>) {
    println("Debug ($head) -> ${knots}:")
    IntRange(0, 4).forEach { y ->
        IntRange(0, 5).forEach { x ->
            if (head.x == x && head.y == y) {
                print("H")
            } else if (knots.any { it.x == x && it.y == y }) {
                print(knots.indexOfFirst { it.x == x && it.y == y } + 1)
            } else {
                print(get(y)?.get(x)?.let { "." } ?: ".")
            }
        }
        println()
    }
}

data class Knot(var x: Int = 0, var y: Int = 4) {

    private fun tooFar(other: Knot) = abs(x - other.x) > 1 || abs(y - other.y) > 1

    fun follow(head: Knot) {
        if (tooFar(head)) {
            if (x == head.x) {
                y += (head.y - y) / 2
            } else if (y == head.y) {
                x += (head.x - x) / 2
            } else {
                if (abs(y - head.y) == 2 && abs(x - head.x) == 2) {
                    x += (head.x - x) / 2
                    y += (head.y - y) / 2
                } else if (abs(y - head.y) == 2) {
                    x = head.x
                    y += (head.y - y) / 2
                } else {
                    y = head.y
                    x += (head.x - x) / 2
                }
            }
        }
    }

    fun left() = x--

    fun right() = x++

    fun up() = y--

    fun down() = y++
}