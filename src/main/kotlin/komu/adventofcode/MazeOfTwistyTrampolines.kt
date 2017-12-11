package komu.adventofcode

fun mazeOfTwistyTrampolines(input: String): Int =
    mazeOfTwistyTrampolines(input.lines().filter { it.isNotEmpty() }.map { it.toInt() })

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
