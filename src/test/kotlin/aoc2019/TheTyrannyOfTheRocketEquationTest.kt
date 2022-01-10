package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TheTyrannyOfTheRocketEquationTest {

    @Test
    fun `part 1`() {
        assertEquals(3390830, theTyrannyOfTheRocketEquation(readTestInput("/2019/TheTyrannyOfTheRocketEquation.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(5083370, theTyrannyOfTheRocketEquation2(readTestInput("/2019/TheTyrannyOfTheRocketEquation.txt")))
    }
}
