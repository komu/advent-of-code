package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SensorBoostTest {

    @Test
    fun `example 1`() {
        val input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
        val machine = IntCodeMachine(input)
        machine.run()
        assertEquals(input, machine.outputToList().joinToString(","))
    }

    @Test
    fun `example 2`() {
        val machine = IntCodeMachine("1102,34915192,34915192,7,4,7,99,0")
        machine.run()
        assertEquals(1219070632396864L, machine.outputToList().last())
    }

    @Test
    fun `example 3`() {
        val machine = IntCodeMachine("104,1125899906842624,99")
        machine.run()
        assertEquals(1125899906842624L, machine.outputToList().last())
    }

    @Test
    fun `part 1`() {
        assertEquals(3429606717L, sensorBoost(1, readTestInput("/2019/SensorBoost.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(33679L, sensorBoost(2, readTestInput("/2019/SensorBoost.txt")))
    }
}
