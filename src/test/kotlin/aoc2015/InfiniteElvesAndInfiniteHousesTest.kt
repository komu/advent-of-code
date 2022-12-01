package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InfiniteElvesAndInfiniteHousesTest {

    private val input = 33100000

    @Test
    fun part1() {
        assertEquals(776160, infiniteElvesAndInfiniteHouses1(input))
    }

    @Test
    fun part2() {
        assertEquals(786240, infiniteElvesAndInfiniteHouses2(input))
    }
}
