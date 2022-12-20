package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day11Test : StringSpec({
    val day = Day11("11_example")

    "solution1" {
        day.solution1() shouldBe 10605
    }

    "solution2" {
        day.solution2() shouldBe 2713310158
    }
})
