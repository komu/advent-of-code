package komu.adventofcode.aoc2018

import komu.adventofcode.utils.Point
import kotlin.test.Test
import kotlin.test.assertEquals

class ModeMazeTest {

    @Test
    fun part1() {
        assertEquals(5637, modeMaze1(depth = 11394, target = Point(7, 701)))
    }

    @Test
    fun part2() {
        assertEquals(969, modeMaze2(depth = 11394, target = Point(7, 701)))
    }
}
