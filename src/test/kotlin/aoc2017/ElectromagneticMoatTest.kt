package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ElectromagneticMoatTest {

    @Test
    fun `example data`() {
        assertEquals(31, electromagneticMoat("""
            0/2
            2/2
            2/3
            3/4
            3/5
            0/1
            10/1
            9/10
        """.trimIndent()))
    }

    @Test
    fun `real data`() {
        assertEquals(1511, electromagneticMoat(readTestInput("/2017/ElectromagneticMoat.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(1471, electromagneticMoat(readTestInput("/2017/ElectromagneticMoat.txt"), longest = true))
    }
}
