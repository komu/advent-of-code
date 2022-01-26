package komu.adventofcode.aoc2018

import komu.adventofcode.utils.nonEmptyLines

@Suppress("SpellCheckingInspection", "UNUSED_ANONYMOUS_PARAMETER")
private class ChronalOp(private val func: (MutableList<Int>, Int, Int) -> Int) {

    fun evaluate(regs: MutableList<Int>, a: Int, b: Int, c: Int) {
        regs[c] = func(regs, a, b)
    }

    companion object {
        private val addr = ChronalOp { regs, a, b -> regs[a] + regs[b] }
        private val addi = ChronalOp { regs, a, b -> regs[a] + b }
        private val mulr = ChronalOp { regs, a, b -> regs[a] * regs[b] }
        private val muli = ChronalOp { regs, a, b -> regs[a] * b }
        private val banr = ChronalOp { regs, a, b -> regs[a] and regs[b] }
        private val bani = ChronalOp { regs, a, b -> regs[a] and b }
        private val borr = ChronalOp { regs, a, b -> regs[a] or regs[b] }
        private val bori = ChronalOp { regs, a, b -> regs[a] or b }
        private val setr = ChronalOp { regs, a, b -> regs[a] }
        private val seti = ChronalOp { regs, a, b -> a }
        private val gtir = ChronalOp { regs, a, b -> if (a > regs[b]) 1 else 0 }
        private val gtri = ChronalOp { regs, a, b -> if (regs[a] > b) 1 else 0 }
        private val gtrr = ChronalOp { regs, a, b -> if (regs[a] > regs[b]) 1 else 0 }
        private val eqir = ChronalOp { regs, a, b -> if (a == regs[b]) 1 else 0 }
        private val eqri = ChronalOp { regs, a, b -> if (regs[a] == b) 1 else 0 }
        private val eqrr = ChronalOp { regs, a, b -> if (regs[a] == regs[b]) 1 else 0 }

        val ops = listOf(addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)
    }
}

fun chronalClassification(input: String) =
    ChronalSample.parseSamples(input).count { s -> ChronalOp.ops.count { op -> s.matches(op) } >= 3 }

fun chronalClassification2(input: String): Int {
    val samples = ChronalSample.parseSamples(input)

    val operatorAssignments = (0..15).associateWith { ChronalOp.ops.toMutableList() }

    for (sample in samples) {
        val assignments = operatorAssignments[sample.instruction.opcode]!!

        val it = assignments.iterator()
        while (it.hasNext()) {
            val op = it.next()
            if (!sample.matches(op))
                it.remove()
        }

        if (assignments.size == 1) {
            propagateRemoval(assignments[0], operatorAssignments.values)
        }
    }

    check(operatorAssignments.values.all { it.size == 1 })
    val finalAssignments = operatorAssignments.mapValues { it.value[0] }
    val program = ChronalClassificationInstruction.parseProgram(input)

    val regs = mutableListOf(0, 0, 0, 0)
    for (instruction in program) {
        val op = finalAssignments[instruction.opcode]!!
        op.evaluate(regs, instruction.a, instruction.b, instruction.c)
    }

    return regs[0]
}

private fun propagateRemoval(chronalOp: ChronalOp, operatorAssignments: Collection<MutableList<ChronalOp>>) {
    for (assignments in operatorAssignments)
        if (assignments.size > 1) {
            assignments.remove(chronalOp)
            if (assignments.size == 1)
                propagateRemoval(assignments[0], operatorAssignments)
        }
}

private class ChronalClassificationInstruction(val opcode: Int, val a: Int, val b: Int, val c: Int) {
    constructor(xs: List<Int>) : this(xs[0], xs[1], xs[2], xs[3])

    companion object {
        private val regex = Regex("""(\d+) (\d+) (\d+) (\d+)""")

        fun parseProgram(input: String) =
            input.substringAfter("\n\n\n").nonEmptyLines().map { parse(it) }

        fun parse(s: String) =
            ChronalClassificationInstruction(regex.matchEntire(s)?.destructured?.toList()?.map { it.toInt() }
                ?: error("invalid '$s'"))
    }
}

private class ChronalSample(
    val before: List<Int>,
    val instruction: ChronalClassificationInstruction,
    val after: List<Int>,
) {

    fun matches(op: ChronalOp): Boolean {
        val registers = before.toMutableList()
        op.evaluate(registers, instruction.a, instruction.b, instruction.c)
        return after == registers
    }

    companion object {
        private val regex =
            Regex("""Before:\s+\[(\d+), (\d+), (\d+), (\d+)]\n(.+)\nAfter:\s+\[(\d+), (\d+), (\d+), (\d+)]""")

        fun parseSamples(input: String) =
            input.substringBefore("\n\n\n").split(Regex("\n\n")).map { parse(it) }

        private fun parse(input: String): ChronalSample {
            val values = regex.matchEntire(input)?.destructured?.toList() ?: error("invalid '$input'")

            return ChronalSample(
                before = values.subList(0, 4).map { it.toInt() },
                instruction = ChronalClassificationInstruction.parse(values[4]),
                after = values.subList(5, 9).map { it.toInt() }
            )
        }
    }
}