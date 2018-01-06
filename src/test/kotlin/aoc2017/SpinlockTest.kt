package komu.adventofcode.aoc2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SpinlockTest {

    @Test
    fun `example input`() {
        assertEquals(638, spinlock1(3))
    }

    @Test
    fun `real input`() {
        assertEquals(417, spinlock1(348))
    }

    @Test
    fun `part 2`() {
        assertEquals(34334221, spinlock2(348))
    }
}
