package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day5Test : StringSpec({

    val stacks = mapOf(
        1 to "ZN",
        2 to "MCD",
        3 to "P"
    )

    val day = Day5("5_example", stacks, 5)

    "solution1" {
        day.solution1() shouldBe "CMZ"
    }

    "solution2" {
        day.solution2() shouldBe "MCD"
    }
})
