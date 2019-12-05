package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SunnyWithAChanceOfAsteroidsTest {

    @Test
    fun `part 1`() {
        assertEquals(13087969, sunnyWithAChangeOfAsteroids(readTestInput("/2019/SunnyWithAChanceOfAsteroids.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(14110739, sunnyWithAChangeOfAsteroids2(readTestInput("/2019/SunnyWithAChanceOfAsteroids.txt")))
    }
}

