package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CorruptionChecksumTest {

    @Test
    fun `calculate example`() {
        assertEquals(18, corruptionChecksum("""
            5 1 9 5
            7 5 3
            2 4 6 8
        """.trimIndent().linesToIntGrid()))
    }

    @Test
    fun `calculate example 2`() {
        assertEquals(9, corruptionChecksum2("""
            5 9 2 8
            9 4 7 3
            3 8 6 5
        """.trimIndent().linesToIntGrid()))
    }

    @Test
    fun `real data`() {
        assertEquals(43074, corruptionChecksum(readIntGrid("input/CorruptionChecksum.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(280, corruptionChecksum2(readIntGrid("input/CorruptionChecksum.txt")))
    }
}