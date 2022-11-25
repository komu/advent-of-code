package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FourDimensionalAdventureTest {

    @Test
    fun example1() {
        assertEquals(
            2,
            fourDimensionalAdventure1(
                """
                0,0,0,0
                3,0,0,0
                0,3,0,0
                0,0,3,0
                0,0,0,3
                0,0,0,6
                9,0,0,0
                12,0,0,0
                """.trimIndent()
            )
        )
    }

    @Test
    fun example2() {
        assertEquals(
            4,
            fourDimensionalAdventure1(
                """
                -1,2,2,0
                0,0,2,-2
                0,0,0,-2
                -1,2,0,0
                -2,-2,-2,2
                3,0,2,-1
                -1,3,2,2
                -1,0,-1,0
                0,2,1,-2
                3,0,0,0
                """.trimIndent()
            )
        )
    }

    @Test
    fun example3() {
        assertEquals(
            3,
            fourDimensionalAdventure1(
                """
                1,-1,0,1
                2,0,-1,0
                3,2,-1,0
                0,0,3,1
                0,0,-1,-1
                2,3,-2,0
                -2,2,0,0
                2,-2,0,-1
                1,-1,0,-1
                3,2,0,2
                """.trimIndent()
            )
        )
    }

    @Test
    fun example4() {
        assertEquals(
            8,
            fourDimensionalAdventure1(
                """
                1,-1,-1,-2
                -2,-2,0,1
                0,2,1,3
                -2,3,-2,1
                0,2,3,-2
                -1,-1,1,-2
                0,-2,-1,0
                -2,2,3,-1
                1,2,2,0
                -1,-2,0,-2
                """.trimIndent()
            )
        )
    }

    @Test
    fun `part 1`() {
        assertEquals(346, fourDimensionalAdventure1(readTestInput("/2018/FourDimensionalAdventure.txt")))
    }

    @Test
    fun `part 2`() {
    }
}
