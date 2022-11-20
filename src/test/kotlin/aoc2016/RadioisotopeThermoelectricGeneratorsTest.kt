package komu.adventofcode.aoc2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RadioisotopeThermoelectricGeneratorsTest {

    @Test
    fun `part 1`() {
        assertEquals(47, radioisotopeThermoelectricGenerators1())
    }

    @Test
    fun `part 2`() {
        assertEquals(71, radioisotopeThermoelectricGenerators2())
    }
}
