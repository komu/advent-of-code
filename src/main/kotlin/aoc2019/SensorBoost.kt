package aoc2019

fun sensorBoost(param: Long, input: String): Long {
    val machine = IntCodeMachine(input)
    machine.sendInput(param)
    machine.run()
    return machine.peekFirstOutput()
}
