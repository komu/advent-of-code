package aoc2019

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Point

fun setAndForget1(input: String): Int {
    val map = createAsciiMap(input)

    fun get(x: Int, y: Int): Char? =
        map.getOrNull(y)?.getOrNull(x)

    var sum = 0
    for ((y, line) in map.withIndex())
        for ((x, c) in line.withIndex())
            if (c == '#' && get(x - 1, y) == '#' && get(x + 1, y) == '#' && get(x, y - 1) == '#' && get(x, y + 1) == '#')
               sum += x*y

    return sum
}

fun verifySolution(input: String, program: String): Boolean {
    val map = createAsciiMap(input)
    val y = map.indexOfFirst { '^' in it }
    val x = map[y].indexOf('^')

    var direction = Direction.UP
    var point = Point(x, y)
    val visited = mutableSetOf(point)

    for (cmd in program.split(',')) {
        when (cmd) {
            "L" -> direction = direction.left
            "R" -> direction = direction.right
            else -> {
                repeat(cmd.toInt()) {
                    point = point.towards(direction, 1)
                    if (map[point.y][point.x] !in "^#")
                        error("invalid point: $point")
                    visited += point
                }
            }
        }
    }

    val scaffoldBlocks = map.sumBy { line -> line.count { it in "^#" } }
    return scaffoldBlocks == visited.size
}

private fun createAsciiMap(input: String): List<String> {
    val machine = IntCodeMachine(input)

    val buffer = StringBuilder()
    machine.writeOutput = { c -> buffer.append(c.toChar()) }
    machine.run()
    return buffer.toString().lines()
}

fun setAndForget2(input: String): Int {
    val machine = IntCodeMachine(input)
    machine.memory[0] = 2

    machine.writeLine("A,A,B,C,B,C,B,C,C,A")
    machine.writeLine("R,8,L,4,R,4,R,10,R,8")
    machine.writeLine("L,12,L,12,R,8,R,8")
    machine.writeLine("R,10,R,4,R,4")
    machine.writeLine("n")

    machine.run()

    return machine.outputToList().last().toInt()
}

fun isValidCompressedSolution(main: String, a: String, b: String, c: String, program: String): Boolean =
    main.length <= 20 && a.length <= 20 && b.length <= 20 && c.length <= 20
            && decompress(main, a, b, c) == program

private fun decompress(main: String, a: String, b: String, c: String): String {
    val commands = mutableListOf<String>()
    for (call in main.split(',')) {
        val subroutine = when (call) {
            "A" -> a
            "B" -> b
            "C" -> c
            else -> error("invalid call $call")
        }
        commands += subroutine.split(',')
    }
    return commands.joinToString(",")
}
