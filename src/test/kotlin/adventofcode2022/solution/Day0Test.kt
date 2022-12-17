package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day0Test : StringSpec({
    val day = Day0("0_example")

    "solution1" {
        day.solution1() shouldBe 0
    }

    "solution2" {
        day.solution2() shouldBe 0
    }
})
