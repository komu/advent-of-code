package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.splitBySpace
import java.util.*

internal sealed interface Value

internal enum class Reg : Value {
    A, B, C, D;

    override fun toString() = name.lowercase()
}

@JvmInline
internal value class Constant(val value: Int) : Value {
    override fun toString(): String = value.toString()
}

internal open class AssembunnyVM(code: String) {
    private val instructions = code.nonEmptyLines().map { AssembunnyInstruction.parse(it) }.toMutableList()
    var ip = 0
    private val registers = EnumMap<Reg, Int>(Reg::class.java)

    open fun step() {
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

    fun matches(vararg expected: AssembunnyInstruction): Boolean {
        if (ip + expected.size >= instructions.size) return false

        return expected.indices.all { i -> expected[i] == instructions[ip + i] }
    }

    fun execute() {
        while (ip < instructions.size) {
            step()
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
}

internal sealed class AssembunnyInstruction {

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

