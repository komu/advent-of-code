package aoc2019

fun sunnyWithAChangeOfAsteroids(input: String): Int {
    val machine = IntCodeMachine(input)

    machine.sendInput(1)
    machine.run()
    return machine.peekLastOutput()
}

fun sunnyWithAChangeOfAsteroids2(input: String): Int {
    val machine = IntCodeMachine(input)

    machine.sendInput(5)
    machine.run()
    return machine.peekLastOutput()
}
