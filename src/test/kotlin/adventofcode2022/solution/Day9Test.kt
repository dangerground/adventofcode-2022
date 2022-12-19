package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day9Test : StringSpec({
    val day = Day9("9_example")

    "solution1" {
        day.solution1() shouldBe 13
    }

    "solution2" {
        Day9("9_example2").solution2() shouldBe 36
    }
})
