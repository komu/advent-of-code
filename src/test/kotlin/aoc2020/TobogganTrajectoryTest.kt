package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TobogganTrajectoryTest {

    @Test
    fun `example 1`() {
        assertEquals(7, tobogganTrajectory(3, 1, """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent()
        ))
    }

    @Test
    fun `part 1`() {
        assertEquals(216, tobogganTrajectory(3, 1, readTestInput("/2020/TobogganTrajectory.txt")))
    }

    @Test
    fun `part 2`() {
        val map = readTestInput("/2020/TobogganTrajectory.txt")
        assertEquals(6708199680, tobogganTrajectory2(map))
    }
}
