package adventofcode2022.solution

import adventofcode2022.util.readDay
import java.util.Objects
import java.util.PriorityQueue

fun main() {
    Day12("12").solve()
}

class Day12(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Int {
        val (map, start, end) = parse()

        val edges = findAllowedEdges(map)

        return edges.route(start, end)
    }

    private fun parse(): Triple<HeightMap, Point, Point> {
        val map = HeightMap()
        var start = Point(-1, -1)
        var end = Point(-1, -1)

        inputText.lines().forEachIndexed { y, line ->
            val row = mutableMapOf<Int, Char>()
            line.toCharArray().forEachIndexed { x, c ->
                when (c) {
                    'S' -> {
                        start = Point(x, y)
                        row[x] = 'a'
                    }

                    'E' -> {
                        end = Point(x, y)
                        row[x] = 'z'
                    }

                    else -> {
                        row[x] = c
                    }
                }
            }
            map[y] = row
        }
        return Triple(map, start, end)
    }

    private fun findAllowedEdges(map: HeightMap): EdgesList {
        val edges = EdgesList()
        for (y in 0 until map.height()) {
            for (x in 0 until map.width()) {
                val allowedHeight = map.get(x, y)!! + 1

                val left = map.get(x - 1, y)
                if (left != null && left <= allowedHeight) {
                    edges.add(Point(x, y), Point(x - 1, y))
                }

                val right = map.get(x + 1, y)
                if (right != null && right <= allowedHeight) {
                    edges.add(Point(x, y), Point(x + 1, y))
                }

                val up = map.get(x, y - 1)
                if (up != null && up <= allowedHeight) {
                    edges.add(Point(x, y), Point(x, y - 1))
                }
                val down = map.get(x, y + 1)
                if (down != null && down <= allowedHeight) {
                    edges.add(Point(x, y), Point(x, y + 1))
                }
            }
        }

        return edges
    }

    fun solution2(): Long {
        return 0
    }
}

data class Point(val x: Int, val y: Int, var cost: Int = 0) : Comparable<Point> {

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    override fun compareTo(other: Point) = cost.compareTo(other.cost)


    override fun equals(other: Any?): Boolean {
        return other is Point
                && other.x == x
                && other.y == y
    }
}

typealias HeightMap = HashMap<Int, Map<Int, Char>>

fun HeightMap.height() = size
fun HeightMap.width() = get(0)?.size ?: 0
fun HeightMap.get(x: Int, y: Int) = get(y)?.get(x)?.code

class EdgesList {
    private val data = mutableMapOf<Point, MutableSet<Point>>()

    fun add(p1: Point, p2: Point) {
        data.putIfAbsent(p1, mutableSetOf())
        data[p1]!!.add(p2)
    }

    fun route(start: Point, goal: Point): Int {
        if (start == goal) {
            println("Found ${start.cost}")
            return start.cost
        }

        val queue = PriorityQueue<Point>()
        val visited = mutableSetOf<Point>()

        queue.add(start)
        visited.add(start)

        var i = 0
        while (queue.isNotEmpty()) {
            i++
            val current = queue.poll()

            if (current == goal) {
                println("Found ${current.cost}")
                return current.cost
            } else {
                data[current]
                    ?.filter { !visited.contains(it) }
                    ?.map { Point(it.x, it.y, current.cost + 1) }
                    ?.filter { !queue.containsPoint(it) }
                    ?.let {
                        queue.addAll(it)
                    }
            }
            visited.add(current)
        }

        return 0
    }

    fun PriorityQueue<Point>.containsPoint(p: Point): Boolean {
        return this.toList().find { it.x == p.x && it.y == p.y && it.cost == p.cost } != null
    }
}