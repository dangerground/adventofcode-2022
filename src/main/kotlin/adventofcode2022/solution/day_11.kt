package adventofcode2022.solution

import adventofcode2022.util.readDay
import java.lang.Long.max

fun main() {
    Day11("11").solve()
}

class Day11(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        val monkeys = realMonkey()
        val monkeyCount = mutableMapOf<Int, Long>()

        repeat(20) {
            monkeys.forEach { x ->
                val monkey = x.value
                monkeyCount[x.key] = (monkeyCount[x.key] ?: 0) + monkey.items.size
                monkey.items
                    .map { monkey.operation(it) }
                    .map { it / 3 }
                    .forEach {
                        val target = if (it % monkey.test == 0L) monkey.ifTrue else monkey.ifFalse
                        monkeys[target]!!.items.add(it)
                    }
                monkey.items.clear()
            }
        }

        monkeys.forEach {
            println("Monkey ${it.key} -> ${it.value.items}")
        }
        val best = monkeyCount.map { it.value }.sortedDescending().take(2)

        return best[0] * best[1]
    }

    private fun realMonkey() = mapOf(
        0 to Monkey(
            items = mutableListOf(57, 58),
            operation = { it * 19 },
            test = 7,
            ifTrue = 2,
            ifFalse = 3,
        ),
        1 to Monkey(
            items = mutableListOf(66, 52, 59, 79, 94, 73),
            operation = { it + 1 },
            test = 19,
            ifTrue = 4,
            ifFalse = 6,
        ),
        2 to Monkey(
            items = mutableListOf(80),
            operation = { it + 6 },
            test = 5,
            ifTrue = 7,
            ifFalse = 5,
        ),
        3 to Monkey(
            items = mutableListOf(82, 81, 68, 66, 71, 83, 75, 97),
            operation = { it + 5 },
            test = 11,
            ifTrue = 5,
            ifFalse = 2,
        ),
        4 to Monkey(
            items = mutableListOf(55, 52, 67, 70, 69, 94, 90),
            operation = { it * it },
            test = 17,
            ifTrue = 0,
            ifFalse = 3,
        ),
        5 to Monkey(
            items = mutableListOf(69, 85, 89, 91),
            operation = { it + 7 },
            test = 13,
            ifTrue = 1,
            ifFalse = 7,
        ),
        6 to Monkey(
            items = mutableListOf(75, 53, 73, 52, 75),
            operation = { it * 7 },
            test = 2,
            ifTrue = 0,
            ifFalse = 4,
        ),
        7 to Monkey(
            items = mutableListOf(94, 60, 79),
            operation = { it + 2 },
            test = 3,
            ifTrue = 1,
            ifFalse = 6,
        )
    )

    private fun exampleMonkeys() = mapOf(
        0 to Monkey(
            items = mutableListOf(79, 98),
            operation = { it * 19 },
            test = 23,
            ifTrue = 2,
            ifFalse = 3,
        ),
        1 to Monkey(
            items = mutableListOf(54, 65, 75, 74),
            operation = { it + 6 },
            test = 19,
            ifTrue = 2,
            ifFalse = 0,
        ),
        2 to Monkey(
            items = mutableListOf(79, 60, 97),
            operation = { it * it },
            test = 13,
            ifTrue = 1,
            ifFalse = 3,
        ),
        3 to Monkey(
            items = mutableListOf(74),
            operation = { it + 3 },
            test = 17,
            ifTrue = 0,
            ifFalse = 1,
        )
    )

    fun solution2(): Long {

        val monkeys = realMonkey()
        val monkeyCount = mutableMapOf<Int, Long>()
        val divide = monkeys.map { it.value.test }.reduce { acc, l -> max(acc, 1) * l }
        println("divide $divide")

        repeat(10_000) {
            monkeys.forEach { x ->
                val monkey = x.value
                monkeyCount[x.key] = (monkeyCount[x.key] ?: 0L) + monkey.items.size
                monkey.items
                    .map { it % divide }
                    .map { monkey.operation(it) }
                    .forEach {
                        if (it % monkey.test == 0L) {
                            monkeys[monkey.ifTrue]!!.items.add(it)
                        } else {
                            monkeys[monkey.ifFalse]!!.items.add(it)
                        }
                    }
                monkey.items.clear()
            }
        }

        /*
        monkeys.forEach {
            println("Monkey ${it.key} -> ${it.value.items}")
        }
*/

        monkeyCount.forEach {
            println("Monkey ${it.key} -> ${it.value}")
        }
        val best = monkeyCount.map { it.value }.sortedDescending().take(2)

        return best[0] * best[1]
    }
}

class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val ifTrue: Int,
    val ifFalse: Int,
)