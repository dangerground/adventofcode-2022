package adventofcode2022.solution

import adventofcode2022.util.readDay
import kotlin.math.max
import kotlin.math.min

fun main() {
    Day14("14").solve()
}

class Day14(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Int {
        val sandCastle = SandCastle()
        parseLineSegments().forEach {
            sandCastle.add(it)
        }

        while (sandCastle.pourSand(500, 0)) {
            // just loop
        }

        sandCastle.debug()
        return sandCastle.countSand()
    }

    private fun parseLineSegments(): MutableList<LineSegment> {
        val segments = mutableListOf<LineSegment>()
        inputText.lines()
            .map { it.split(" -> ").map { it.parsePoint() } }
            .forEach { linePoints ->
                for (i in 0 until linePoints.size - 1) {
                    segments.add(LineSegment(linePoints[i], linePoints[i + 1]))
                }
            }
        return segments
    }

    fun solution2(): Int {
        val sandCastle = SandCastle()
        parseLineSegments().forEach {
            sandCastle.add(it)
        }

        val height = sandCastle.maxY + 2
        val startX = 500
        val floor = LineSegment(Point(startX - height, height), Point(startX + height, height))
        println("floor: $floor")
        sandCastle.add(floor)

        while (sandCastle.pourSand(500, 0)) {
            // just loop
        }

        sandCastle.debug()
        return sandCastle.countSand()
    }
}

data class LineSegment(val p1: Point, val p2: Point) {
    override fun toString(): String {
        return "${p1.str()} -> ${p2.str()}"
    }
}

fun String.parsePoint() = split(",").let { Point(it[0].toInt(), it[1].toInt()) }

fun Point.str() = "$x,$y"


enum class SandType { ROCK, SAND }

class SandCastle : HashMap<Int, MutableMap<Int, SandType>>() {

    private var maxX = 0
    private var minX = 9999
    var maxY = 0
    private var minY = 0

    fun add(l: LineSegment) {
        minX = min(minX, min(l.p1.x, l.p2.x))
        //minY = min(minY, min(l.p1.y, l.p2.y))
        maxX = max(maxX, max(l.p1.x, l.p2.x))
        maxY = max(maxY, max(l.p1.y, l.p2.y))

        val yRange = if (l.p1.y < l.p2.y) IntRange(l.p1.y, l.p2.y) else IntRange(l.p2.y, l.p1.y)
        val xRange = if (l.p1.x < l.p2.x) IntRange(l.p1.x, l.p2.x) else IntRange(l.p2.x, l.p1.x)

        for (y in yRange) {
            for (x in xRange) {
                set(x, y, SandType.ROCK)
            }
        }
    }

    private fun set(x: Int, y: Int, type: SandType) {
        putIfAbsent(y, mutableMapOf())
        get(y)!![x] = type
    }

    private fun get(x: Int, y: Int) = get(y)?.get(x)

    fun debug() {
        println("Debug: ($minX,$minY)-($maxX,$maxY)")
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val type = get(x, y)
                if (type == SandType.ROCK) {
                    print("#")
                } else if (type == SandType.SAND) {
                    print("O")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun countSand() = values.sumOf { it.values.count { it == SandType.SAND } }
    fun pourSand(startX: Int, startY: Int): Boolean {
        //debug()
        var falling = true
        var x = startX
        var y = startY
        do {
            if (get(x, y + 1) == null) {
               // println("down $$x,$y)")
                y++
            } else if (get(x - 1, y + 1) == null) {
               // println("left $$x,$y)")
                y++
                x--
            } else if (get(x + 1, y + 1) == null) {
                //println("right $$x,$y)")
                y++
                x++
            } else {
                if (x == startX && y == startY) {
                    set(x, y, SandType.SAND)
                    return false
                } else {
                    falling = false
                }
            }

            if (y > maxY) {
                println("maxxx $$x,$y )$maxY")
                return false
            }

        } while (falling)
//        println("add $x,$y")
        set(x, y, SandType.SAND)

        return true
    }

}
