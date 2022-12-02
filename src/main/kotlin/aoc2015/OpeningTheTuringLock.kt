package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

private typealias Reg = String

fun openingTheTuringLock1(input: String): Int {
    val vm = TuringLockVm.load(input)
    vm.run()
    return vm.get("b").toInt()
}

fun openingTheTuringLock2(input: String): Int {
    val vm = TuringLockVm.load(input)
    vm.set("a", 1)
    vm.run()
    return vm.get("b").toInt()
}

private class TuringLockVm(private val instructions: List<TuringLockInstruction>) {

    private var pc = 0
    private val registers = mutableMapOf<Reg, Long>()

    fun get(reg: Reg) = registers[reg] ?: 0

    fun set(reg: Reg, value: Long) {
        registers[reg] = value
    }

    fun run() {
        while (pc in instructions.indices)
            eval(instructions[pc++])
    }

    fun eval(inst: TuringLockInstruction) {
        when (inst) {
            is TuringLockInstruction.Hlf -> set(inst.r, get(inst.r) / 2)
            is TuringLockInstruction.Tpl -> set(inst.r, get(inst.r) * 3)
            is TuringLockInstruction.Inc -> set(inst.r, get(inst.r) + 1)
            is TuringLockInstruction.Jmp -> jump(inst.offset)
            is TuringLockInstruction.Jie -> if (get(inst.r) % 2 == 0L) jump(inst.offset)
            is TuringLockInstruction.Jio -> if (get(inst.r) == 1L) jump(inst.offset)
        }
    }

    private fun jump(offset: Int) {
        pc = pc - 1 + offset
    }

    companion object {
        fun load(input: String) =
            TuringLockVm(input.nonEmptyLines().map { TuringLockInstruction.parse(it) })
    }
}

private sealed class TuringLockInstruction {
    data class Hlf(val r: Reg) : TuringLockInstruction()
    data class Tpl(val r: Reg) : TuringLockInstruction()
    data class Inc(val r: Reg) : TuringLockInstruction()
    data class Jmp(val offset: Int) : TuringLockInstruction()
    data class Jie(val r: Reg, val offset: Int) : TuringLockInstruction()
    data class Jio(val r: Reg, val offset: Int) : TuringLockInstruction()

    companion object {
        fun parse(s: String): TuringLockInstruction {
            val parts = s.split(" ")
            return when (parts[0]) {
                "hlf" -> Hlf(parts[1])
                "tpl" -> Tpl(parts[1])
                "inc" -> Inc(parts[1])
                "jmp" -> Jmp(parts[1].toInt())
                "jie" -> Jie(parts[1].removeSuffix(","), parts[2].toInt())
                "jio" -> Jio(parts[1].removeSuffix(","), parts[2].toInt())
                else -> error("unknown instruction '$s")
            }
        }
    }
}
