package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day8Test : StringSpec({
    val day = Day8("8_example")

    "solution1" {
        day.solution1() shouldBe 21
    }

    "solution2" {
        day.solution2() shouldBe 8
    }
})
