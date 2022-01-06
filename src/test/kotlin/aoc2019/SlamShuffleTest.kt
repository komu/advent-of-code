package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SlamShuffleTest {

    @Test
    fun `part 1`() {
        assertEquals(3377, slamShuffle(readTestInput("/2019/SlamShuffle.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(29988879027217, slamShuffle2(readTestInput("/2019/SlamShuffle.txt")))
    }
}
