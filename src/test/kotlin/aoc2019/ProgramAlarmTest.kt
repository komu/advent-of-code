package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProgramAlarmTest {

    @Test
    fun `part 1`() {
        assertEquals(6087827, programAlarm(readTestInput("/2019/ProgramAlarm.txt"), noun = 12, verb = 2))
    }

    @Test
    fun `part 2`() {
        assertEquals(5379, programAlarm2(readTestInput("/2019/ProgramAlarm.txt"), expected = 19690720))
    }
}
