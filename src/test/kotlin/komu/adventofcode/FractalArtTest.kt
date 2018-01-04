package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FractalArtTest {

    @Test
    fun `example input`() {
        assertEquals(12, fractalArt("""
            ../.# => ##./#../...
            .#./..#/### => #..#/..../..../#..#
        """.trimIndent(), 2))
    }

    @Test
    fun `real input`() {
        assertEquals(110, fractalArt(readTestInput("/input/FractalArt.txt"), 5))
    }

    @Test
    fun `real input 2`() {
        assertEquals(1277716, fractalArt(readTestInput("/input/FractalArt.txt"), 18))
    }
}
