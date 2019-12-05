package aoc2019

import komu.adventofcode.utils.pow10

class IntCodeMachine(initialMemory: List<Int>) {

    private val memory = initialMemory.toMutableList()
    private var ip = 0

    constructor(input: String) : this(input.trim().split(",").map { it.toInt() })

    var input = 0
    val size: Int
        get() = memory.size

    val output = mutableListOf<Int>()

    fun clone() = IntCodeMachine(memory)

    fun init(noun: Int, verb: Int) {
        memory[1] = noun
        memory[2] = verb
    }

    fun run(): Int {
        ip = 0
        while (true) {
            val opcode = memory[ip]
            when (opcode % 100) {
                1 -> {
                    val dst = memory[ip + 3]
                    memory[dst] = param(1) + param(2)
                    ip += 4
                }
                2 -> {
                    val dst = memory[ip + 3]
                    memory[dst] = param(1) * param(2)
                    ip += 4
                }
                3 -> {
                    val dst = memory[ip + 1]
                    memory[dst] = input
                    ip += 2
                }
                4 -> {
                    output += param(1)
                    ip += 2
                }
                5 ->
                    if (param(1) != 0)
                        ip = param(2)
                    else
                        ip += 3
                6 ->
                    if (param(1) == 0)
                        ip = param(2)
                    else
                        ip += 3
                7 -> {
                    memory[memory[ip + 3]] = if (param(1) < param(2)) 1 else 0
                    ip += 4
                }
                8 -> {
                    memory[memory[ip + 3]] = if (param(1) == param(2)) 1 else 0
                    ip += 4
                }
                99 ->
                    return memory[0]
                else ->
                    error("unknown code $opcode")
            }
        }
    }

    private fun param(index: Int): Int {
        val op = memory[ip]
        val mode = (op / pow10(index + 1)) % 10
        return resolve(memory[ip + index], mode)
    }

    private fun resolve(value: Int, mode: Int) = when (mode) {
        0 -> memory[value]
        1 -> value
        else -> error("invalid addressing mode $mode for op ${memory[ip]}")
    }
}
