package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SpaceImageFormatTest {

    @Test
    fun `part 1`() {
        assertEquals(1320, spaceImageFormat1(25, 6, readTestInput("/2019/SpaceImageFormat.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals("""
           |   ###  ## ###  ## #   ##
           | ## # ## # ###  # ## ## #
           | ## # ##### # #  ### ## #
           |   ## ###### ## # ##   ##
           | # ## ## ### ## # ## # ##
           | ## ##  #### ## ## # ## #
        """.trimMargin(), spaceImageFormat2(25, 6, readTestInput("/2019/SpaceImageFormat.txt")))
    }
}
