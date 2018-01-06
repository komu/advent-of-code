package komu.adventofcode.aoc2017

fun mazeOfTwistyTrampolines(input: String): Int =
    mazeOfTwistyTrampolines(input.lines().filter { it.isNotEmpty() }.map { it.toInt() })

fun mazeOfTwistyTrampolines2(input: String): Int =
    mazeOfTwistyTrampolines2(input.lines().filter { it.isNotEmpty() }.map { it.toInt() })

fun mazeOfTwistyTrampolines(offsets: List<Int>): Int {
    val instructions = offsets.toIntArray()

    var steps = 0
    var pc = 0

    while (pc in instructions.indices) {
        pc += instructions[pc]++
        steps++
    }

    return steps
}

fun mazeOfTwistyTrampolines2(offsets: List<Int>): Int {
    val instructions = offsets.toIntArray()

    var steps = 0
    var pc = 0

    while (pc in instructions.indices) {
        val offset = instructions[pc]
        if (offset >= 3)
            instructions[pc]--
        else
            instructions[pc]++
        pc += offset
        steps++
    }

    return steps
}
