package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MonitoringStationTest {

    @Test
    fun `example 1`() {
        assertEquals(
            8, monitoringStation1(
                """
                .#..#
                .....
                #####
                ....#
                ...##
                """.trimIndent()
            )
        )
    }

    @Test
    fun `example 2`() {
        assertEquals(
            33, monitoringStation1(
                """
                ......#.#.
                #..#.#....
                ..#######.
                .#.#.###..
                .#..#.....
                ..#....#.#
                #..#....#.
                .##.#..###
                ##...#..#.
                .#....####
                """.trimIndent()
            )
        )
    }

    @Test
    fun `example 3`() {
        assertEquals(
            35, monitoringStation1(
                """
                #.#...#.#.
                .###....#.
                .#....#...
                ##.#.#.#.#
                ....#.#.#.
                .##..###.#
                ..#...##..
                ..##....##
                ......#...
                .####.###.
                """.trimIndent()
            )
        )
    }

    @Test
    fun `part 1`() {
        assertEquals(288, monitoringStation1(readTestInput("/2019/MonitoringStation.txt")))
    }

    @Test
    fun `example 4`() {
        assertEquals(802, monitoringStation2(
            """
                .#..##.###...#######
                ##.############..##.
                .#.######.########.#
                .###.#######.####.#.
                #####.##.#.##.###.##
                ..#####..#.#########
                ####################
                #.####....###.#.#.##
                ##.#################
                #####.##.###..####..
                ..######..##.#######
                ####.##.####...##..#
                .#####..#.######.###
                ##...#.##########...
                #.##########.#######
                .####.#.###.###.#.##
                ....##.##.###..#####
                .#.#.###########.###
                #.#.#.#####.####.###
                ###.##.####.##.#..##
                """.trimIndent()
        ))
    }

    @Test
    fun `part 2`() {
        assertEquals(616, monitoringStation2(readTestInput("/2019/MonitoringStation.txt")))
    }
}
