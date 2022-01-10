package komu.adventofcode.aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DockingDataTest {

    @Test
    fun `example 1`() {
        assertEquals(
            165, dockingData1(
                """
                mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                mem[8] = 11
                mem[7] = 101
                mem[8] = 0
                """.trimIndent()
            )
        )
    }

    @Test
    fun `example 2`() {
        assertEquals(
            208, dockingData2(
                """
                mask = 000000000000000000000000000000X1001X
                mem[42] = 100
                mask = 00000000000000000000000000000000X0XX
                mem[26] = 1
                """.trimIndent()
            )
        )
    }

    @Test
    fun `part 1`() {
        assertEquals(13727901897109, dockingData1(readTestInput("/2020/DockingData.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(5579916171823, dockingData2(readTestInput("/2020/DockingData.txt")))
    }
}
