package aoc2020

import komu.adventofcode.utils.nonEmptyLines

fun handheldHalting1(input: String): Int {
    val vm = VM(Instruction.parseProgram(input))
    vm.run()
    return vm.accumulator
}

fun handheldHalting2(input: String): Int {
    for (instructions in Instruction.parseProgram(input).modified()) {
        val vm = VM(instructions)
        if (vm.run())
            return vm.accumulator
    }

    error("no result")
}

private fun List<Instruction>.modified(): Sequence<List<Instruction>> = sequence {
    for ((i, instruction) in withIndex()) {
        val swapped = swap(instruction) ?: continue

        yield(subList(0, i) + swapped + subList(i + 1, size))
    }
}

private fun swap(instruction: Instruction): Instruction? = when (instruction) {
    is Instruction.Nop -> Instruction.Jmp(instruction.value)
    is Instruction.Jmp -> Instruction.Nop(instruction.value)
    is Instruction.Acc -> null
}

private class VM(private val instructions: List<Instruction>) {
    var accumulator = 0
    private var pc = 0
    private val seen = Array(instructions.size) { false }

    fun run(): Boolean {
        while (pc < instructions.size) {
            if (seen[pc])
                return false
            seen[pc] = true
            when (val instruction = instructions[pc]) {
                is Instruction.Acc -> {
                    accumulator += instruction.value
                    pc += 1
                }
                is Instruction.Nop ->
                    pc += 1
                is Instruction.Jmp ->
                    pc += instruction.value
            }
        }

        return true
    }
}

private sealed class Instruction {
    class Nop(val value: Int) : Instruction()
    class Acc(val value: Int) : Instruction()
    class Jmp(val value: Int) : Instruction()

    companion object {

        private val regex = Regex("""(\w+) ([-+]\d+)""")

        fun parseProgram(program: String): List<Instruction> =
            program.nonEmptyLines().map { parse(it) }

        private fun parse(line: String): Instruction {
            val (_, op, arg) = regex.matchEntire(line)?.groupValues ?: error("invalid line '$line'")
            val value = arg.toInt()
            return when (op) {
                "nop" -> Nop(value)
                "acc" -> Acc(value)
                "jmp" -> Jmp(value)
                else -> error("invalid instruction '$op'")
            }
        }
    }
}
