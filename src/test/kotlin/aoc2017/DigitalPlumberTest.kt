package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DigitalPlumberTest {

    private val example = """
            0 <-> 2
            1 <-> 1
            2 <-> 0, 3, 4
            3 <-> 2, 4
            4 <-> 2, 3, 6
            5 <-> 6
            6 <-> 4, 5
        """.trimIndent()

    @Test
    fun `group size in example`() {
        assertEquals(6, pipesConnectedTo(0, example))
    }

    @Test
    fun `groups in example data`() {
        assertEquals(2, totalPipeGroups(example))
    }

    @Test
    fun `real data`() {
        assertEquals(128, pipesConnectedTo(0, readTestInput("/2017/DigitalPlumber.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(209, totalPipeGroups(readTestInput("/2017/DigitalPlumber.txt")))
    }
}
