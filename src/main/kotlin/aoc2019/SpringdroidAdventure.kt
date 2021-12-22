package komu.adventofcode.aoc2019

import aoc2019.IntCodeMachine
import komu.adventofcode.utils.nonEmptyLines

private fun evalSpringCode(input: String, code: String): Int {
    val machine = IntCodeMachine(input)

    for (line in code.trimIndent().nonEmptyLines())
        machine.writeLine(line)

    var result: Int = -1
    machine.writeOutput = { value ->
        if (value < 128) {
            print(value.toInt().toChar())
            System.out.flush()
        } else {
            result = value.toInt()
        }
    }

    machine.run()

    return result
}

fun springdroidAdventure(input: String) = evalSpringCode(input, """
    NOT A J
    NOT B T
    OR T J
    NOT C T
    OR T J
    AND D J
    WALK
""")

fun springdroidAdventure2(input: String) = evalSpringCode(input, """
    OR E T
    OR H T
    AND D T
    OR T J
    NOT A T
    NOT T T
    AND B T
    AND C T
    NOT T T
    AND T J
    RUN
""")

