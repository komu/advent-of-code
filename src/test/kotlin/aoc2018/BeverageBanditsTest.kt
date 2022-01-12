package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BeverageBanditsTest {

    @Test
    fun `example 1`() {
        assertEquals(27730, beverageBandits("""
            #######
            #.G...#
            #...EG#
            #.#.#G#
            #..G#E#
            #.....#
            #######
        """.trimIndent()))
    }

    @Test
    fun `example 2`() {
        assertEquals(36334, beverageBandits("""
            #######
            #G..#E#
            #E#E.E#
            #G.##.#
            #...#E#
            #...E.#
            #######
        """.trimIndent()))
    }

    @Test
    fun `example 3`() {
        assertEquals(39514, beverageBandits("""
            #######
            #E..EG#
            #.#G.E#
            #E.##E#
            #G..#.#
            #..E#.#
            #######
        """.trimIndent()))
    }

    @Test
    fun `example 4`() {
        assertEquals(27755, beverageBandits("""
            #######
            #E.G#.#
            #.#G..#
            #G.#.G#
            #G..#.#
            #...E.#
            #######
        """.trimIndent()))
    }

    @Test
    fun `example 5`() {
        assertEquals(28944, beverageBandits("""
            #######
            #.E...#
            #.#..G#
            #.###.#
            #E#G#G#
            #...#G#
            #######
        """.trimIndent()))
    }

    @Test
    fun `example 6`() {
        assertEquals(18740, beverageBandits("""
            #########
            #G......#
            #.E.#...#
            #..##..G#
            #...##..#
            #...#...#
            #.G...G.#
            #.....G.#
            #########
          """.trimIndent()))
    }

    @Test
    fun `part 1`() {
        assertEquals(221754, beverageBandits(readTestInput("/2018/BeverageBandits.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(41972, beverageBandits2(readTestInput("/2018/BeverageBandits.txt")))
    }
}
