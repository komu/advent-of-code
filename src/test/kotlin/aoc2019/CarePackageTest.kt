package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CarePackageTest {

    @Test
    fun `part 1`() {
        assertEquals(326, carePackage1(readTestInput("/2019/CarePackage.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(15988, carePackage2(readTestInput("/2019/CarePackage.txt")))
    }
}
