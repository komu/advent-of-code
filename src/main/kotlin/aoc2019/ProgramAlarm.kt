package aoc2019

fun programAlarm(input: String, noun: Int, verb: Int): Int {
    val machine = IntCodeMachine(input)

    machine.init(noun, verb)

    return machine.run()
}

fun programAlarm2(input: String, expected: Int): Int {
    val base = IntCodeMachine(input)

    for (noun in 0 until base.size)
        for (verb in 0 until base.size) {
            val machine = base.clone()
            machine.init(noun, verb)
            if (machine.run() == expected)
                return 100 * noun + verb
        }

    error("no result")
}
