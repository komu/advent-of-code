package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class WizardSimulatorTest {

    @Test
    fun part1() {
        assertEquals(953, wizardSimulator())
    }

    @Test
    fun part2() {
        assertEquals(1289, wizardSimulator(hard = true))
    }
}
