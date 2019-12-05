package aoc2019

fun sunnyWithAChangeOfAsteroids(input: String): Int {
    val machine = IntCodeMachine(input)

    machine.input = 1
    machine.run()
    return machine.output.last()
}

fun sunnyWithAChangeOfAsteroids2(input: String): Int {
    val machine = IntCodeMachine(input)

    machine.input = 5
    machine.run()
    return machine.output.last()
}
