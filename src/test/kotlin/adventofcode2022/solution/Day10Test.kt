package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day10Test : StringSpec({
    val day = Day10("10_example")

    "solution1" {
        day.solution1() shouldBe 13140
    }

    "solution2" {
        day.solution2() shouldBe 0
    }
})
