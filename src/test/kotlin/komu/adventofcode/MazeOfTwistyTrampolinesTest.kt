package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MazeOfTwistyTrampolinesTest {

    @Test
    fun `example input`() {
        assertEquals(5, mazeOfTwistyTrampolines(listOf(0, 3, 0, 3, -1)))
    }

    @Test
    fun `real data`() {
        assertEquals(359348, mazeOfTwistyTrampolines(readTestInput("input/MazeOfTwistyTrampolines.txt")))
    }
}