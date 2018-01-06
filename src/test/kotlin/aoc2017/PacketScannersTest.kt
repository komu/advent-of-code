package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PacketScannersTest {

    @Test
    fun `example data`() {
        assertEquals(24, packetScannersSeverity("""
            0: 3
            1: 2
            4: 4
            6: 4
        """.trimIndent()))
    }

    @Test
    fun `example data 2`() {
        assertEquals(10, packetScannersMinimumDelay("""
            0: 3
            1: 2
            4: 4
            6: 4
        """.trimIndent()))
    }

    @Test
    fun `real data`() {
        assertEquals(648, packetScannersSeverity(readTestInput("/2017/PacketScanners.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(3933124, packetScannersMinimumDelay(readTestInput("/2017/PacketScanners.txt")))
    }
}
