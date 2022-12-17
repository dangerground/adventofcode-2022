package adventofcode2022.solution

import adventofcode2022.util.readDay
import java.util.Objects

fun main() {
    Day7("7").solve()
}

class Day7(private val num: String) {

    private val inputText = readDay(num)

    private val startNumeric = Regex("^\\d+.*$")

    fun solve() {
        println("Day $num Solution")
        println("* Part 1: ${solution1()}")
        println("* Part 2: ${solution2()}")
    }

    fun solution1(): Long {
        val (root, dirs) = checkDirectorySizes()

        return dirs.filter { it.size() <= 100000 }.sumOf { it.size() }
    }

    private fun checkDirectorySizes(): Pair<Element, MutableList<Element>> {
        val dirs = mutableListOf<Element>()
        val root = Element(
            name = "",
            path = "/",
            parent = null,
            children = mutableSetOf()
        )
        var currentDir = root

        inputText.lines().drop(1).forEach {
            when {
                it.startsWith("\$ cd") -> {
                    val name = it.substring(5)
                    if (name == "..") {
                        currentDir = currentDir.parent!!
                    } else {
                        currentDir =
                            currentDir.children.find { it.name == name || (name == "/" && it.parent == null) }!!
                    }
                    //println("change to: $currentPath")
                }

                it.startsWith("\$ ls") -> {
                    //println("list")
                }

                it.startsWith("dir ") -> {
                    val name = it.substring(4)
                    //println("detect dir: $name")
                    val el = Element(
                        name = name,
                        path = elementInPath(currentDir.path, name),
                        parent = currentDir,
                    )
                    currentDir.add(el)
                    dirs.add(el)
                }

                it.matches(startNumeric) -> {
                    val name = it.substringAfter(' ')
                    val size = it.substringBefore(' ').toLong()
                    //println("detect dir: ${dirs[currentPath]!!}")
                    currentDir.add(
                        Element(
                            name = name,
                            path = elementInPath(currentDir.path, name),
                            parent = currentDir,
                            size = size,
                        )
                    )
                }

                else -> println("unknwon $it (${it.matches(startNumeric)})")
            }
        }
        return Pair(root, dirs)
    }

    private fun elementInPath(currentPath: String, name: String): String {
        return currentPath.trimEnd('/') + '/' + name.trim('/')
    }

    fun solution2(): Long {
        val totalSpace = 70000000
        val requiredSpace = 30000000

        val (root, dirs) = checkDirectorySizes()

        val usedSpace = (totalSpace - root.size())
        val toDeleteSpace =  requiredSpace - usedSpace

        println("used $usedSpace :: missing $toDeleteSpace")

        return dirs.filter { it.size() > toDeleteSpace }
            .minByOrNull { it.size() }!!
            .size()
    }
}


data class Element(
    val name: String,
    val path: String,
    val parent: Element?,
    val children: MutableSet<Element> = mutableSetOf(),
    val size: Long = 0,
) {
    fun size(): Long {
        return (children.sumOf { it.size() } ?: 0L) + size
    }

    fun add(el: Element) {
        children.add(el)
    }

    override fun equals(other: Any?): Boolean {
        return Objects.equals(this, other)
    }
    override fun hashCode(): Int {
        return Objects.hash(path, name)
    }

    override fun toString() = if (size == 0L) {
        "dir $name:\n'$children'\n"
    } else {
        "file (name=$name; size=$size)\n"
    }
}
