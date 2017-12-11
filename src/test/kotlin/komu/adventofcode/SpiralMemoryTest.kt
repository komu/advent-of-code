package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SpiralMemoryTest {

    @Test
    fun `calculate examples`() {
        assertEquals(0, spiralMemorySteps(1))
        assertEquals(3, spiralMemorySteps(12))
        assertEquals(2, spiralMemorySteps(23))
        assertEquals(31, spiralMemorySteps(1024))
    }

    @Test
    fun `correct answer`() {
        assertEquals(419, spiralMemorySteps(289326))
    }

    @Test
    fun `print coordinates`() {
        assertEquals(295229, spiralPart2(289326))
    }
}