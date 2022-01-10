package komu.adventofcode.aoc2019

fun programAlarm(input: String, noun: Long, verb: Long): Long {
    val machine = IntCodeMachine(input)

    machine.memory[1] = noun
    machine.memory[2] = verb
    machine.run()
    return machine.memory[0]
}

fun programAlarm2(input: String, expected: Int): Int {
    val base = IntCodeMachine(input)

    for (noun in 0 until base.memory.size)
        for (verb in 0 until base.memory.size) {
            val machine = base.clone()
            machine.memory[1] = noun.toLong()
            machine.memory[2] = verb.toLong()
            machine.run()
            if (machine.memory[0].toInt() == expected)
                return 100 * noun + verb
        }

    error("no result")
}
