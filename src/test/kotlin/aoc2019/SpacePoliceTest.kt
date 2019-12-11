package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SpacePoliceTest {

    @Test
    fun `part 1`() {
        assertEquals(2293, spacePolice1(readTestInput("/2019/SpacePolice.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals("  ##  #  # #     ##  ###  ###   ##  #      \n" +
                " #  # #  # #    #  # #  # #  # #  # #      \n" +
                " #  # #### #    #    #  # #  # #  # #      \n" +
                " #### #  # #    #    ###  ###  #### #      \n" +
                " #  # #  # #    #  # #    # #  #  # #      \n" +
                " #  # #  # ####  ##  #    #  # #  # ####   \n", spacePolice2(readTestInput("/2019/SpacePolice.txt"))
        )
    }
}
