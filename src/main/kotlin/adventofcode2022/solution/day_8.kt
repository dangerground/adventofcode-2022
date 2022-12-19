package adventofcode2022.solution

import adventofcode2022.util.readDay

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
        forest: MutableMap<Int, MutableMap<Int, Pair<Int, Boolean>>>
    ) {
        var lastHeight = -1
        range.forEach { x ->
            val tree = forest[row]?.get(x)
            val height = tree?.first
            if (height != null && height > lastHeight) {
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
        }.toMap().toMutableMap()

        val width = forest.size
        val height = forest[0]?.size ?: 0

        println("Dimension: $width X $height")

        // check trees
        IntRange(0, width - 1).forEach { x ->
            IntRange(0, height - 1).forEach { y ->
                val score = getScore(forest, x, y)
                forest[y]?.set(x, Pair(height, score))
            }
        }

        // debug
        val bestScore = forest.maxOf {
            it.value
                .map { it.value.second }
                .filterNotNull()
                .max()
        }
        IntRange(0, width - 1).forEach { x ->
            IntRange(0, height).forEach { y ->
                print(forest[x]?.get(y)?.second)
            }
            println()
        }

        return bestScore.toLong()
    }

    private fun getScore(forest: MutableMap<Int, MutableMap<Int, Pair<Int, Int?>>>, startX: Int, startY: Int): Int {
        val treeHeight = forest[startX]?.get(startY)?.second ?: return 0
        println("xxxx")

        var lastLeft = treeHeight
        var leftDistance = 0
        IntRange(0, startX).reversed().forEach { x ->
            val current = forest[x]?.get(startY)?.second
            if (current != null && current > lastLeft) {
                leftDistance = startX - x
                lastLeft = current
            }
        }
        var lastRight = treeHeight
        var rightDistance = 0
        IntRange(startX, forest.size).forEach { x ->
            val current = forest[x]?.get(startY)?.second
            if (current != null && current > lastRight) {
                rightDistance = x - startX
                lastRight = current
            }
        }

        var lastTop = treeHeight
        var topDistance = 0
        IntRange(0, startY).reversed().forEach { y ->
            val current = forest[startX]?.get(y)?.second
            if (current != null && current > lastTop) {
                topDistance = startY - y
                lastTop = current
            }
        }

        var lastBottom = treeHeight
        var bottomDistance = 0
        IntRange(startY, forest[0]!!.size).forEach { y ->
            val current = forest[startX]?.get(y)?.second
            if (current != null && current > lastBottom) {
                bottomDistance = y - startY
                lastBottom = current
            }
        }

        val score = leftDistance * rightDistance * topDistance * bottomDistance
        println("($startX, $startY) = $treeHeight -> $leftDistance * $rightDistance * $topDistance * $bottomDistance = $score")
        return score
    }
}