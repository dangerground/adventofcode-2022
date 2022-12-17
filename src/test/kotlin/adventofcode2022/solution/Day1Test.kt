package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day1Test : StringSpec({
    val day = Day1("1_example")

    "solution1" {
        day.solution1() shouldBe 24000
    }

    "solution2" {
        day.solution2() shouldBe 45000
    }
})
