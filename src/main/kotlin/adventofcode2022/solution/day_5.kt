package adventofcode2022.solution

import adventofcode2022.util.readDay

private const val DAY_NUM = 5

fun main() {
    val stacks = mapOf(
        1 to "DBJV",
        2 to "PVBWRDF",
        3 to "RGFLDCWQ",
        4 to "WJPMLNDB",
        5 to "HNBPCSQ",
        6 to "RDBSNG",
        7 to "ZBPMQFSH",
        8 to "WLF",
        9 to "SVFMR",
    )
    Day5(DAY_NUM.toString(), stacks, 10).solve()
}

class Day5(
    private val num: String,
    stacks: Map<Int, String>,
    private val skipInput: Int,
) {

    private val inputText = readDay(num)
    private val stack = stacks.mapValues { ArrayDeque(it.value.asSequence().toList()) }


    fun solve() {
        println("Day $num Solution")
        //println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): String {
        inputText.lines().drop(skipInput).forEach {
            val (move, from, to) = movements(it)
            repeat(move) {
                stack[from]!!.removeLast().let { pulled ->
                    stack[to]!!.addLast(pulled)
                }
            }
        }
        return stack.values.map { it.last() }.joinToString(separator = "") { it.toString() }
    }

    private fun movements(it: String): Triple<Int, Int, Int> {
        val move = it.replace(Regex("^move (\\d+) from.*$"), "\$1").toInt()
        val from = it.replace(Regex("^.*from (\\d+) to.*$"), "\$1").toInt()
        val to = it.replace(Regex("^.* to (\\d+)$"), "\$1").toInt()
        return Triple(move, from, to)
    }

    fun solution2(): String {
        inputText.lines().drop(skipInput).forEach {
            val (move, from, to) = movements(it)
            IntRange(1, move)
                .mapNotNull {
                    stack[from]!!.removeLast()
                }
                .reversed()
                .forEach {
                    stack[to]!!.addLast(it)
                }
        }
        return stack.values.map { it.last() }.joinToString(separator = "") { it.toString() }
    }
}