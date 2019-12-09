package aoc2019

fun programAlarm(input: String, noun: Int, verb: Int): Int {
    val machine = IntCodeMachine(input)

    machine.init(noun, verb)

    return machine.run().toInt()
}

fun programAlarm2(input: String, expected: Int): Int {
    val base = IntCodeMachine(input)

    for (noun in 0 until base.memoryUse)
        for (verb in 0 until base.memoryUse) {
            val machine = base.clone()
            machine.init(noun, verb)
            if (machine.run().toInt() == expected)
                return 100 * noun + verb
        }

    error("no result")
}
