package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day12Test : StringSpec({
    val day = Day12("12_example")

    "solution1" {
        day.solution1() shouldBe 31
    }

    "solution2" {
        day.solution2() shouldBe 0
    }
})
