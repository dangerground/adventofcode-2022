package adventofcode2022.solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day13Test : StringSpec({
    val day = Day13("13_example")


    "parse" {
        "[]".parsePacket().toString() shouldBe "[]"
        "[[1]]".parsePacket().toString() shouldBe "[[1]]"
        "[[1]]".parsePacket().toString() shouldBe "[[1]]"
        "[[1],4]".parsePacket().toString() shouldBe "[[1], 4]"
        "[[1],[2,3,4]]".parsePacket().toString() shouldBe "[[1], [2, 3, 4]]"
    }

    "compare" {
        "[]".parsePacket().compareTo("[3]".parsePacket()) shouldBe -1
        "[9]".parsePacket().compareTo("[[8,7,6]]".parsePacket()) shouldBe 1
        "[[1],[2,3,4]]".parsePacket().compareTo("[[1],4]".parsePacket()) shouldBe -1
    }

    "solution1" {
        day.solution1() shouldBe 13
    }

    "solution2" {
        day.solution2() shouldBe 140
    }
})
