package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RegularMapTest {

    @Test
    fun `example 1`() {
        assertEquals(3, regularMap("^WNE\$"))
    }

    @Test
    fun `example 2`() {
        assertEquals(10, regularMap("^ENWWW(NEEE|SSE(EE|N))\$"))
    }

    @Test
    fun `example 3`() {
        assertEquals(18, regularMap("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN\$"))
    }

    @Test
    fun `example 4`() {
        assertEquals(23, regularMap("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$"))
    }

    @Test
    fun `example 5`() {
        assertEquals(31, regularMap("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$"))
    }

    @Test
    fun `part 1`() {
        assertEquals(3872, regularMap(readTestInput("/2018/RegularMap.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(8600, regularMap2(readTestInput("/2018/RegularMap.txt")))
    }
}

