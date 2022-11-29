package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.splitBySpace
import java.util.*

fun safeCracking(input: String, eggs: Int): Int {
    val vm = AssembunnyVM.load(input)

    vm.set(Reg.A, eggs)
    vm.execute()

    return vm.get(Reg.A)
}

private sealed interface Value

private enum class Reg : Value {
    A, B, C, D;

    override fun toString() = name.lowercase()
}

@JvmInline
private value class Constant(val value: Int) : Value {
    override fun toString(): String = value.toString()
}

private class AssembunnyVM(instructions: List<AssembunnyInstruction>) {
    private val instructions = instructions.toMutableList()

    private var ip = 0
    private val registers = EnumMap<Reg, Int>(Reg::class.java)

    fun execute() {
        while (ip < instructions.size) {
            if (ip == 4
                && instructions[4] == AssembunnyInstruction.Cpy(Reg.B, Reg.C)
                && instructions[5] == AssembunnyInstruction.Inc(Reg.A)
                && instructions[6] == AssembunnyInstruction.Dec(Reg.C)
                && instructions[7] == AssembunnyInstruction.Jnz(Reg.C, -2)
                && instructions[8] == AssembunnyInstruction.Dec(Reg.D)
                && instructions[9] == AssembunnyInstruction.Jnz(Reg.D, -5)
            ) {
                set(Reg.A, get(Reg.B) * get(Reg.D))
                set(Reg.C, 0)
                set(Reg.D, 0)
                ip = 10
            }

            val oldIp = ip
            when (val inst = instructions[ip++]) {
                is AssembunnyInstruction.Cpy ->
                    registers[inst.y] = eval(inst.x)
                is AssembunnyInstruction.Inc ->
                    registers[inst.x] = eval(inst.x) + 1
                is AssembunnyInstruction.Dec ->
                    registers[inst.x] = eval(inst.x) - 1
                is AssembunnyInstruction.Jnz ->
                    if (eval(inst.x) != 0)
                        ip = oldIp + eval(inst.y)
                is AssembunnyInstruction.Toggle -> {
                    val index = oldIp + eval(inst.x)
                    if (index in instructions.indices)
                        instructions[index] = instructions[index].toggle()
                }
                AssembunnyInstruction.Invalid -> {}
            }
        }
    }

    fun get(reg: Reg) =
        registers[reg] ?: 0

    fun set(reg: Reg, value: Int) {
        registers[reg] = value
    }

    private fun eval(value: Value): Int = when (value) {
        is Reg -> get(value)
        is Constant -> value.value
    }

    companion object {
        fun load(input: String) =
            AssembunnyVM(input.nonEmptyLines().map { AssembunnyInstruction.parse(it) })
    }
}

private sealed class AssembunnyInstruction {

    fun toggle() = when (this) {
        is Inc -> Dec(x)
        is Dec -> Inc(x)
        is Toggle -> if (x is Reg) Inc(x) else Invalid
        is Cpy -> Jnz(x, y)
        is Jnz -> if (y is Reg) Cpy(x, y) else Invalid
        Invalid -> Invalid
    }

    data class Cpy(val x: Value, val y: Reg) : AssembunnyInstruction()
    data class Inc(val x: Reg) : AssembunnyInstruction()
    data class Dec(val x: Reg) : AssembunnyInstruction()
    data class Jnz(val x: Value, val y: Value) : AssembunnyInstruction() {
        constructor(x: Value, y: Int): this(x, Constant(y))
    }
    data class Toggle(val x: Value) : AssembunnyInstruction()
    object Invalid : AssembunnyInstruction()

    companion object {
        fun parse(s: String): AssembunnyInstruction {
            val parts = s.splitBySpace()

            return when (parts[0]) {
                "cpy" -> Cpy(parts[1].toValue(), parts[2].toReg())
                "inc" -> Inc(parts[1].toReg())
                "dec" -> Dec(parts[1].toReg())
                "jnz" -> Jnz(parts[1].toValue(), parts[2].toValue())
                "tgl" -> Toggle(parts[1].toValue())
                else -> error("invalid instruction '$s'")
            }
        }

        private fun String.toValue(): Value =
            if (this[0].isDigit() || this[0] == '-') Constant(toInt()) else toReg()

        private fun String.toReg(): Reg =
            Reg.values().find { it.name.lowercase() == this } ?: error("invalid register: '$this'")
    }
}
