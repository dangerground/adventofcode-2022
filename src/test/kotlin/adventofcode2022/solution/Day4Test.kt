package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day4Test : StringSpec({
    val day = Day4("4_example")

    "solution1" {
        day.solution1() shouldBe 2
    }

    "solution2" {
        day.solution2() shouldBe 4
    }
})
