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
        """.trimIndent()))
    }
}