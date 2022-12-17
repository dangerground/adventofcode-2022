package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day7Test : StringSpec({
    val day = Day7("7_example")

    "solution1" {
        day.solution1() shouldBe 95437
    }

    "solution2" {
        day.solution2() shouldBe 24933642
    }
})
