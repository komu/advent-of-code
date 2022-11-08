package komu.adventofcode.aoc2018

class VM(private val ipReg: Int, private val instructions: List<Instruction>) {

    val regs = mutableListOf(0, 0, 0, 0, 0, 0)

    val currentInstruction: Instruction?
        get() = instructions.getOrNull(regs[ipReg])

    fun run() {
        while (true) {
            if (!step())
                break
        }
    }

    fun step(): Boolean {
        val instruction = currentInstruction ?: return false
        instruction.evaluate(regs)
        regs[ipReg] += 1
        return true
    }

    @Suppress("unused")
    fun dumpInstructions() {
        for ((i, ins) in instructions.withIndex())
            println("$i -> $ins")
    }

    companion object {
        fun load(input: String): VM {
            val lines = input.lines()
            val ipReg = lines[0].removePrefix("#ip ").toInt()
            val instructions = lines.drop(1).map { Instruction.parse(it) }
            return VM(ipReg, instructions)
        }
    }
}

class Instruction(val opcode: OpCode, val a: Int, val b: Int, val c: Int) {

    fun evaluate(regs: MutableList<Int>) {
        opcode.evaluate(regs, a, b, c)
    }

    override fun toString() = opcode.dump(a, b, c)

    companion object {
        private val regex = Regex("""(\w+) (\d+) (\d+) (\d+)""")

        fun parse(s: String): Instruction {
            val (op, a, b, c) = regex.matchEntire(s)?.destructured ?: error("invalid '$s'")
            val opcode = OpCode.values().find { it.name.equals(op, ignoreCase = true) } ?: error("invalid op '$op'")
            return Instruction(opcode, a.toInt(), b.toInt(), c.toInt())
        }
    }
}


@Suppress("SpellCheckingInspection", "UNUSED_ANONYMOUS_PARAMETER", "EnumEntryName")
enum class OpCode {

    addr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] + regs[b]
        override fun dump(a: Int, b: Int) = "r$a + r$b"
    },

    addi {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] + b
        override fun dump(a: Int, b: Int) = "r$a + $b"
    },

    mulr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] * regs[b]
        override fun dump(a: Int, b: Int) = "r$a * r$b"
    },

    muli {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] * b
        override fun dump(a: Int, b: Int) = "r$a * $b"
    },

    banr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] and regs[b]
        override fun dump(a: Int, b: Int) = "r$a and r$b"
    },

    bani {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] and b
        override fun dump(a: Int, b: Int) = "r$a and $b"
    },

    borr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] or regs[b]
        override fun dump(a: Int, b: Int) = "r$a or r$b"
    },

    bori {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a] or b
        override fun dump(a: Int, b: Int) = "r$a or $b"
    },

    setr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = regs[a]
        override fun dump(a: Int, b: Int) = "r$a"
    },

    seti {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = a
        override fun dump(a: Int, b: Int) = "$a"
    },

    gtir {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = if (a > regs[b]) 1 else 0
        override fun dump(a: Int, b: Int) = "$a gt r$b"
    },

    gtri {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = if (regs[a] > b) 1 else 0
        override fun dump(a: Int, b: Int) = "r$a gt $b"
    },

    gtrr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = if (regs[a] > regs[b]) 1 else 0
        override fun dump(a: Int, b: Int) = "r$a gt r$b"
    },

    eqir {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = if (a == regs[b]) 1 else 0
        override fun dump(a: Int, b: Int) = "$a eq r$b"
    },

    eqri {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = if (regs[a] == b) 1 else 0
        override fun dump(a: Int, b: Int) = "r$a eq $b"
    },

    eqrr {
        override fun evaluate(regs: MutableList<Int>, a: Int, b: Int) = if (regs[a] == regs[b]) 1 else 0
        override fun dump(a: Int, b: Int) = "r$a eq r$b"
    };

    fun evaluate(regs: MutableList<Int>, a: Int, b: Int, c: Int) {
        regs[c] = evaluate(regs, a, b)
    }

    fun dump(a: Int, b: Int, c: Int) =
        "r$c = " + dump(a, b)

    abstract fun evaluate(regs: MutableList<Int>, a: Int, b: Int): Int
    abstract fun dump(a: Int, b: Int): String
}