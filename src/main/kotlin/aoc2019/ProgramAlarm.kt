package aoc2019

class IntCodeMachine(initialMemory: List<Int>) {

    private val memory = initialMemory.toMutableList()

    constructor(input: String): this(input.trim().split(",").map { it.toInt() })

    val size: Int
        get() = memory.size

    fun clone() = IntCodeMachine(memory)

    fun run(noun: Int, verb: Int): Int {
        memory[1] = noun
        memory[2] = verb
        var ip = 0
        while (true) {
            when (val opcode = memory[ip]) {
                1 -> {
                    val l = memory[ip + 1]
                    val r = memory[ip + 2]
                    val dst = memory[ip + 3]
                    memory[dst] = memory[l] + memory[r]
                    ip += 4
                }
                2 -> {
                    val l = memory[ip + 1]
                    val r = memory[ip + 2]
                    val dst = memory[ip + 3]
                    memory[dst] = memory[l] * memory[r]
                    ip += 4
                }
                99 -> {
                    return memory[0]
                }
                else ->
                    error("unknown code $opcode")
            }
        }
    }
}

fun programAlarm(input: String, noun: Int, verb: Int): Int {
    val machine = IntCodeMachine(input)

    return machine.run(noun, verb)
}

fun programAlarm2(input: String, expected: Int): Int {
    val base = IntCodeMachine(input)

    for (noun in 0 until base.size)
        for (verb in 0 until base.size) {
            val machine = base.clone()
            if (machine.run(noun, verb) == expected)
                return 100 * noun + verb
        }

    error("no result")
}
