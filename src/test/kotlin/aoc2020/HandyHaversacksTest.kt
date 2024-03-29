package komu.adventofcode.aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HandyHaversacksTest {

    @Test
    fun `example 1`() {
        val data = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent()

        assertEquals(4, handyHaversacks1(data))
    }

    @Test
    fun `part 1`() {
        assertEquals(248, handyHaversacks1(readTestInput("/2020/HandyHaversacks.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(57281, handyHaversacks2(readTestInput("/2020/HandyHaversacks.txt")))
    }
}
