package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReservoirResearchTest {

    @Test
    fun `example 1`() {
        assertEquals(57, reservoirResearch("""
            x=495, y=2..7
            y=7, x=495..501
            x=501, y=3..7
            x=498, y=2..4
            x=506, y=1..2
            x=498, y=10..13
            x=504, y=10..13
            y=13, x=498..504
        """.trimIndent()))
    }

    @Test
    fun `example 2`() {
        assertEquals(29, reservoirResearch2("""
            x=495, y=2..7
            y=7, x=495..501
            x=501, y=3..7
            x=498, y=2..4
            x=506, y=1..2
            x=498, y=10..13
            x=504, y=10..13
            y=13, x=498..504
        """.trimIndent()))
    }

    @Test
    fun `part 1`() {
        assertEquals(30384, reservoirResearch(readTestInput("/2018/ReservoirResearch.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(24479, reservoirResearch2(readTestInput("/2018/ReservoirResearch.txt")))
    }
}
