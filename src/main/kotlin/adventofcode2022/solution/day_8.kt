package adventofcode2022.solution

import adventofcode2022.util.readDay
import kotlin.math.max

fun main() {
    Day8("8").solve()
}

class Day8(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        val forest = inputText.lines().mapIndexed { y, line ->
            y to line.toCharArray().mapIndexed { x, tree ->
                x to Pair(tree.digitToInt(), false)
            }.toMap().toMutableMap()
        }.toMap().toMutableMap()

        val width = forest.size
        val height = forest[0]?.size ?: 0

        println("Dimension: $width X $height")

        // check by line
        IntRange(0, width - 1).forEach { x ->
            val column = IntRange(0, height)
            updateRow(x, column, forest)
            updateRow(x, column.reversed(), forest)
        }

        // check by coliumn
        IntRange(0, height - 1).forEach { y ->
            val column = IntRange(0, width)
            updateColumn(y, column, forest)
            updateColumn(y, column.reversed(), forest)
        }


        // debug
        var visible = 0
        IntRange(0, width - 1).forEach { x ->
            IntRange(0, height).forEach { y ->
                if ((forest[x]?.get(y)?.second == true)) {
                    //print("x")
                    visible++
                    //} else {
                    //    print(".")
                }
            }
            //println()
        }

        return visible.toLong()
    }

    private fun updateRow(
        row: Int,
        range: IntProgression,
        forest: Forest<Boolean>
    ) {
        var lastHeight = -1
        range.forEach { x ->
            val height = forest[row]?.get(x)?.first ?: 0
            if (height > lastHeight) {
                forest[row]?.set(x, Pair(height, true))
                lastHeight = height
            }
        }
    }

    private fun updateColumn(
        column: Int,
        range: IntProgression,
        forest: MutableMap<Int, MutableMap<Int, Pair<Int, Boolean>>>
    ) {
        var lastHeight = -1
        range.forEach { y ->
            val tree = forest[y]?.get(column)
            val height = tree?.first
            if (height != null && height > lastHeight) {
                forest[y]?.set(column, Pair(height, true))
                lastHeight = height
            }
        }
    }

    fun solution2(): Long {

        val forest = inputText.lines().mapIndexed { y, line ->
            y to line.toCharArray().mapIndexed { x, tree ->
                x to Pair(tree.digitToInt(), null as Int?)
            }.toMap().toMutableMap()
        }.toMap().toMutableMap() as Forest<Int>

        val width = forest.size
        val height = forest[0]?.size ?: 0

        println("Dimension: $width X $height")

        val widthRange = IntRange(0, width - 1)
        val heightRange = IntRange(0, height - 1)

        // check trees
        heightRange.forEach { y ->
            widthRange.forEach { x ->
                val score = getScore(forest, x, y)
                forest[y]?.set(x, Pair(forest.height(x, y)!!, score))
            }
        }

        // debug
        val bestScore = forest.maxOf {
            it.value
                .map { it.value.second }
                .maxOf { it }
        }
        println("---------")

        heightRange.forEach { y ->
            widthRange.forEach { x ->
                print("." + forest.height(x, y))
            }
            println()
        }
        println("---------")

        return bestScore.toLong()
    }

    private fun getScore(forest: Forest<Int>, startX: Int, startY: Int): Int {
        val treeHeight = forest.height(startX, startY) ?: 0

        var leftDistance = 1
        val leftRange = IntRange(-2, startX - 1)
        for (x in leftRange.reversed()) {
            val current = forest.height(x, startY)
            if (current == null) {
                leftDistance = startX - x - 1
                break
            } else if (current >= treeHeight) {
                leftDistance = startX - x
                break
            }
        }
        leftDistance = max(leftDistance, 1)

        var rightDistance = 1
        val rightRange = IntRange(startX + 1, forest.size)
        for (x in rightRange) {
            val current = forest.height(x, startY)
            if (current == null) {
                rightDistance = x - startX - 1
                break
            } else if (current >= treeHeight) {
                rightDistance = x - startX
                break
            }
        }
        rightDistance = max(rightDistance, 1)

        var topDistance = 1
        val topRange = IntRange(-1, startY - 1)
        for (y in topRange.reversed()) {
            val current = forest.height(startX, y)
            if (current == null) {
                topDistance = startY - y - 1
                break
            } else if (current >= treeHeight) {
                topDistance = startY - y
                break
            }
        }
        topDistance = max(topDistance, 1)

        var bottomDistance = 1
        val bottomRange = IntRange(startY + 1, forest[0]!!.size)
        for (y in bottomRange) {
            val current = forest.height(startX, y)
            if (current == null) {
                bottomDistance = y - startY - 1
                break
            } else if (current >= treeHeight) {
                bottomDistance = y - startY
                break
            }
        }
        bottomDistance = max(bottomDistance, 1)

        return leftDistance * rightDistance * topDistance * bottomDistance
    }
}

typealias Forest<T> = MutableMap<Int, MutableMap<Int, Pair<Int, T>>>

fun Forest<Int>.height(x: Int, y: Int) = this[y]?.get(x)?.first
fun Forest<Int>.score(x: Int, y: Int) = this[y]?.get(x)?.second