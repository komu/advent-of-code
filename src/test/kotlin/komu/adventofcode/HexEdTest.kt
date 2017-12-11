package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HexEdTest {

    @Test
    fun `example input`() {
        assertEquals(3, hexEdDistance("ne,ne,ne"))
        assertEquals(0, hexEdDistance("ne,ne,sw,sw"))
        assertEquals(2, hexEdDistance("ne,ne,s,s"))
        assertEquals(3, hexEdDistance("se,sw,se,sw,sw"))
    }

    @Test
    fun `puzzle input`() {
        assertEquals(715, hexEdDistance(readTestInput("input/HexEd.txt")))
    }

    @Test
    fun `puzzle input 2`() {
        assertEquals(1512, hexEdMaxDistance(readTestInput("input/HexEd.txt")))
    }
}