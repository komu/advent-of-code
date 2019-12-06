package aoc2019

import aoc201.universalOrbitMap1
import aoc201.universalOrbitMap2
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UniversalOrbitMapTest {

    @Test
    fun `part 1 - example`() {
        assertEquals(42, universalOrbitMap1("COM)B B)C C)D D)E E)F B)G G)H D)I E)J J)K K)L".replace(' ', '\n')))
    }

    @Test
    fun `part 1`() {
        assertEquals(154386, universalOrbitMap1(readTestInput("/2019/UniversalOrbitMap.txt")))
    }

    @Test
    fun `part 2 - example`() {
        assertEquals(4, universalOrbitMap2("COM)B B)C C)D D)E E)F B)G G)H D)I E)J J)K K)L K)YOU I)SAN".replace(' ', '\n')))
    }

    @Test
    fun `part 2`() {
        assertEquals(346, universalOrbitMap2(readTestInput("/2019/UniversalOrbitMap.txt")))
    }
}
