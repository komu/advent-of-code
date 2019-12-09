package aoc2019

import java.math.BigInteger

fun sensorBoost(param: Int, input: String): BigInteger {
    val machine = IntCodeMachine(input)
    machine.sendInput(param)
    machine.run()
    return machine.peekFirstOutput()
}
