package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day2Test : StringSpec({
    val day = Day2("2_example")

    "solution1" {
        day.solution1() shouldBe 15
    }

    "solution2" {
        day.solution2() shouldBe 12
    }
})
