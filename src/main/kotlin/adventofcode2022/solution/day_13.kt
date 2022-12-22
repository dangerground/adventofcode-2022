package adventofcode2022.solution

import adventofcode2022.util.batchedList
import adventofcode2022.util.readDay

fun main() {
    Day13("13").solve()
}

class Day13(private val num: String) {

    private val inputText = readDay(num)

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Int {
        return batchedList(inputText.lines()).mapIndexed { index, it ->
            println("\n== Pair ${index + 1} ==")
            val left = it[0].parsePacket()
            val right = it[1].parsePacket()

//            println("compare: = $left vs $right")
            val x = left.compareTo(right)
            if (x < 0) println("- Left side is smaller, so inputs are in the right order")
            else println("- Right side is smaller, so inputs are not in the right order")
            x
        }.mapIndexed { index, it -> if (it < 0) index + 1 else 0 }
            .sum()
    }

    fun solution2(): Long {
        return 0
    }
}

fun String.parsePacket(): Entry {
    var current = Entry()
    var currentNum = ""
    toCharArray().forEach {
        when {
            it.isDigit() -> {
                currentNum += "$it"
            }

            it == '[' -> {
                val next = Entry(list = mutableListOf())
                current.add(next)
                current = next
            }

            it == ']' -> {
                if (currentNum.isNotBlank()) {
                    current.add(currentNum)
                    currentNum = ""
                }
                current = current.parent!!
            }

            it == ',' -> {
                if (currentNum.isNotBlank()) {
                    current.add(currentNum)
                    currentNum = ""
                }
            }

            else -> {
                throw UnsupportedOperationException("should not happen: $it")
            }
        }
    }

    return current.list!!.first()
}


data class Entry(
    val num: Int? = null,
    var list: MutableList<Entry>? = null,
    var parent: Entry? = null
) : Comparable<Entry> {

    fun add(num: String) {
        if (list == null) {
            list = mutableListOf()
        }
        list?.add(Entry(num = num.toInt()))
    }

    fun add(entry: Entry) {
        entry.parent = this
        if (list == null) {
            list = mutableListOf()
        }
        list?.add(entry)
    }

    override fun compareTo(other: Entry): Int {
        println("- Compare $this vs $other")
        if (num != null && other.num != null) {
            //println("num ${num.compareTo(other.num)}")
            return num.compareTo(other.num)
        }

        if (num != null && list?.isEmpty() == true) {
            //println("none")
            return -1
        }

        if (list == null) {
            println("- Mixed types; convert left to [$this] and retry comparison")

            return Entry(list = mutableListOf(this)).compareTo(other)
        }

        if (other.list == null) {
            println("- Mixed types; convert right to [$other] and retry comparison")
            return this.compareTo(Entry(list = mutableListOf(other)))
        }

        //println("left vs right: $left vs $right")


        //println("sub $this vs $other")
        var index = 0
        while (list?.getOrNull(index) != null) {
            //println("next $list [$index] (${other.list?.getOrNull(index)})")
            val right = other.list?.getOrNull(index) ?: return 1
            //println("next $list [$index]")
            val result = (list?.get(index))!!.compareTo(right)
            if (result < 0) {
                //println("return -1")
                return -1
            } else if (result > 0) {
                //println("return 1")
                return 1
            }
            //println("next $index ")
            index++
        }

        //println("fail $list")
        return if (other.list?.getOrNull(index) != null) -1 else 0
    }

    override fun toString(): String {
        return if (num != null) "$num" else if (list != null) "$list" else ""
    }

}