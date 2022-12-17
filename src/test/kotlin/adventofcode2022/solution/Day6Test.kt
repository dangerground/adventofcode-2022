package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day6Test : StringSpec({
    val day = Day6("6_example1")

    "solution1" {
        Day6("6_example1").solution1() shouldBe 7
        Day6("6_example2").solution1() shouldBe 5
        Day6("6_example3").solution1() shouldBe 6
        Day6("6_example4").solution1() shouldBe 10
        Day6("6_example5").solution1() shouldBe 11
    }

    "solution2" {
        Day6("6_example1").solution2() shouldBe 19
        Day6("6_example2").solution2() shouldBe 23
        Day6("6_example3").solution2() shouldBe 23
        Day6("6_example4").solution2() shouldBe 29
        Day6("6_example5").solution2() shouldBe 26
    }
})
