package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BinaryBoardingTest {

    @Test
    fun examples() {
        assertEquals(357, parseBoardingPassCode("FBFBBFFRLR"))
        assertEquals(567, parseBoardingPassCode("BFFFBBFRRR"))
        assertEquals(119, parseBoardingPassCode("FFFBBBFRRR"))
        assertEquals(820, parseBoardingPassCode("BBFFBBFRLL"))
    }

    @Test
    fun `part 1`() {
        assertEquals(965, binaryBoarding1(readTestInput("/2020/BinaryBoarding.txt").nonEmptyLines()))
    }

    @Test
    fun `part 2`() {
        assertEquals(524, binaryBoarding2(readTestInput("/2020/BinaryBoarding.txt").nonEmptyLines()))
    }
}
