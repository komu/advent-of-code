package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KnotHashTest {

    @Test
    fun `example data`() {
        assertEquals(12, knotHashChecksum(listOf(3, 4, 1, 5), size = 5))
    }

    @Test
    fun `real data`() {
        assertEquals(20056, knotHashChecksum(listOf(83, 0, 193, 1, 254, 237, 187, 40, 88, 27, 2, 255, 149, 29, 42, 100)))
    }

    @Test
    fun `knothash 2`() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", knotHash(""))
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", knotHash("AoC 2017"))
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", knotHash("1,2,3"))
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", knotHash("1,2,4"))
    }

    @Test
    fun `knothash 2 - real`() {
        assertEquals("d9a7de4a809c56bf3a9465cb84392c8e", knotHash("83,0,193,1,254,237,187,40,88,27,2,255,149,29,42,100"))
    }
}
