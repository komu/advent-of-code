package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConwayCubesTest {

    @Test
    fun `example 1`() {
        assertEquals(112, conwayCubes("""
            .#.
            ..#
            ###
        """.trimIndent(), fourDee = false))
    }

    @Test
    fun `example 2`() {
        assertEquals(848, conwayCubes("""
            .#.
            ..#
            ###
        """.trimIndent(), fourDee = true))
    }

    @Test
    fun `part 1`() {
        assertEquals(280, conwayCubes(readTestInput("/2020/ConwayCubes.txt"), fourDee = false))
    }

    @Test
    fun `part 2`() {
        assertEquals(1696, conwayCubes(readTestInput("/2020/ConwayCubes.txt"), fourDee = true))
    }
}
