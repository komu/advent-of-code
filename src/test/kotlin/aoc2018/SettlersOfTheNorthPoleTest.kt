package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SettlersOfTheNorthPoleTest {

    @Test
    fun `example 1`() {
        assertEquals(1147, settlersOfTheNorthPoleTest("""
            .#.#...|#.
            .....#|##|
            .|..|...#.
            ..|#.....#
            #.#|||#|#|
            ...#.||...
            .|....|...
            ||...#|.#|
            |.||||..|.
            ...#.|..|.
        """.trimIndent()))
    }

    @Test
    fun `part 1`() {
        assertEquals(384480, settlersOfTheNorthPoleTest(readTestInput("/2018/SettlersOfTheNorthPole.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(177004, settlersOfTheNorthPoleTest2(readTestInput("/2018/SettlersOfTheNorthPole.txt")))
    }
}
