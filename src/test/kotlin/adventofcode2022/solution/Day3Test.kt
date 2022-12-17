package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day3Test : StringSpec({
    val day = Day3("3_example")

    "solution1" {
        day.solution1() shouldBe 157
    }

    "solution2" {
        day.solution2() shouldBe 70
    }
})
