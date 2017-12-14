package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DiskDefragmentationTest {

    @Test
    fun `example input used squares`() {
        assertEquals(8108, diskDefragmentationUsedSquares("flqrgnkx"))
    }

    @Test
    fun `real input used squares`() {
        assertEquals(8148, diskDefragmentationUsedSquares("vbqugkhl"))
    }

    @Test
    fun `example input regions`() {
        assertEquals(1242, diskDefragmentationRegions("flqrgnkx"))
    }

    @Test
    fun `real input regions`() {
        assertEquals(1180, diskDefragmentationRegions("vbqugkhl"))
    }
}
