package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MazeOfTwistyTrampolinesTest {

    @Test
    fun `example input`() {
        assertEquals(5, mazeOfTwistyTrampolines(listOf(0, 3, 0, 3, -1)))
    }

    @Test
    fun `real data`() {
        assertEquals(359348, mazeOfTwistyTrampolines(readTestInput("/2017/MazeOfTwistyTrampolines.txt")))
    }

    @Test
    fun `real data2`() {
        assertEquals(27688760, mazeOfTwistyTrampolines2(readTestInput("/2017/MazeOfTwistyTrampolines.txt")))
    }
}
