package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MemoryReallocationTest {

    @Test
    fun `example data`() {
        assertEquals(5, memoryReallocation(listOf(0, 2, 7, 0)))
    }

    @Test
    fun `real data`() {
        assertEquals(4074, memoryReallocation("11\t11\t13\t7\t0\t15\t5\t5\t4\t4\t1\t1\t7\t1\t15\t11"))
    }
}