package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PerfectlySphericalHousesInAVacuumTest {

    @Test
    fun `part 1`() {
        assertEquals(2592, visitedHouses(readTestInput("/2015/PerfectlySphericalHousesInAVacuum.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(2360, visitedHouses2(readTestInput("/2015/PerfectlySphericalHousesInAVacuum.txt")))
    }
}
