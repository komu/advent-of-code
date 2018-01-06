package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SeriesOfTubesTest {

    @Test
    fun `example input`() {
        assertEquals(Pair("ABCDEF", 38), seriesOfTubes("""
             |
             |  +--+
             A  |  C
         F---|----E|--+
             |  |  |  D
             +B-+  +--+
        """.trimIndent()))
    }

    @Test
    fun `real input`() {
        assertEquals(Pair("SXPZDFJNRL", 18126), seriesOfTubes(readTestInput("/2017/SeriesOfTubes.txt")))
    }
}
