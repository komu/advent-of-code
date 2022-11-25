package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ExperimentalEmergencyTeleportationTest {

    @Test
    fun `example 1`() {
        assertEquals(
            7,
            experimentalEmergencyTeleportation1(
                """
                pos=<0,0,0>, r=4
                pos=<1,0,0>, r=1
                pos=<4,0,0>, r=3
                pos=<0,2,0>, r=1
                pos=<0,5,0>, r=3
                pos=<0,0,3>, r=1
                pos=<1,1,1>, r=1
                pos=<1,1,2>, r=1
                pos=<1,3,1>, r=1
                """.trimIndent()
            )
        )
    }

    @Test
    fun `example 2`() {
        assertEquals(
            36,
            experimentalEmergencyTeleportation2(
                """
                pos=<10,12,12>, r=2
                pos=<12,14,12>, r=2
                pos=<16,12,12>, r=4
                pos=<14,14,14>, r=6
                pos=<50,50,50>, r=200
                pos=<10,10,10>, r=5
                """.trimIndent()
            )
        )
    }

    @Test
    fun `part 1`() {
        assertEquals(704, experimentalEmergencyTeleportation1(readTestInput("/2018/ExperimentalEmergencyTeleportation.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(111960222, experimentalEmergencyTeleportation2(readTestInput("/2018/ExperimentalEmergencyTeleportation.txt")))
    }
}
