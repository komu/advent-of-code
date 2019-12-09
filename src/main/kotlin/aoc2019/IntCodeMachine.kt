package aoc2019

import komu.adventofcode.utils.pow10
import java.math.BigInteger
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.atomic.AtomicReference

private typealias Num = BigInteger

class IntCodeMachine private constructor(private val memory: Memory) {

    private var ip: Num = ZERO
    private var relativeBase: Num = ZERO

    constructor(input: String) : this(Memory(input.trim().split(",").map { it.toBigInteger() }))

    private val input = LinkedBlockingDeque<Num>()
    private val output = LinkedBlockingDeque<Num>()
    private var lastOutput = AtomicReference<Num>(ZERO)

    val memoryUse: Int
        get() = memory.size

    fun peekFirstOutput(): Num =
        output.first

    fun peekLastOutput(): Num =
        lastOutput.get()

    fun sendInput(vararg values: Num) {
        for (value in values)
            input.addLast(value)
    }

    fun sendInput(vararg values: Int) {
        sendInput(*values.map { it.toNum() }.toTypedArray())
    }

    fun readNext(): Num =
        output.take()

    fun outputToList(): List<Num> =
        output.toList()

    fun clone() = IntCodeMachine(memory.clone())

    fun init(noun: Int, verb: Int) {
        memory[1.toNum()] = noun.toNum()
        memory[2.toNum()] = verb.toNum()
    }

    fun run(): Num {
        ip = ZERO
        while (true) {
            val opcode = memory[ip]
            when (opcode.toInt() % 100) {
                1 -> {
                    memory[absoluteAddress(3)] = param(1) + param(2)
                    ip += 4
                }
                2 -> {
                    memory[absoluteAddress(3)] = param(1) * param(2)
                    ip += 4
                }
                3 -> {
                    memory[absoluteAddress(1)] = input.takeFirst()
                    ip += 2
                }
                4 -> {
                    writeOutput(param(1))
                    ip += 2
                }
                5 ->
                    if (param(1) != ZERO)
                        ip = param(2)
                    else
                        ip += 3
                6 ->
                    if (param(1) == ZERO)
                        ip = param(2)
                    else
                        ip += 3
                7 -> {
                    memory[absoluteAddress(3)] = if (param(1) < param(2)) ONE else ZERO
                    ip += 4
                }
                8 -> {
                    memory[absoluteAddress(3)] = if (param(1) == param(2)) ONE else ZERO
                    ip += 4
                }
                9 -> {
                    relativeBase += param(1)
                    ip += 2
                }
                99 ->
                    return memory[ZERO]
                else ->
                    error("unknown code $opcode")
            }
        }
    }

    private fun writeOutput(value: Num) {
        output.put(value)
        lastOutput.set(value)
    }

    private fun param(index: Int): Num {
        val op = memory[ip].toInt()
        val mode = (op / pow10(index + 1)) % 10
        return resolve(memory[ip + index], mode)
    }

    private fun absoluteAddress(index: Int): Num {
        val op = memory[ip].toInt()
        val mode = (op / pow10(index + 1)) % 10
        val value = memory[ip + index]
        return when (mode) {
            0 -> value
            1 -> error("can't resolve absolute address of immediate")
            2 -> relativeBase + value
            else -> error("invalid addressing mode $mode for op ${memory[ip]}")
        }
    }

    private fun resolve(value: Num, mode: Int) = when (mode) {
        0 -> memory[value]
        1 -> value
        2 -> memory[relativeBase + value]
        else -> error("invalid addressing mode $mode for op ${memory[ip]}")
    }

    private class Memory(initial: List<Num>) {
        private val memory = initial.toMutableList()

        operator fun get(i: Num): Num =
            memory.getOrNull(i.toInt()) ?: ZERO

        operator fun set(index: Num, value: Num) {
            val i = index.toInt()
            while (i >= memory.size)
                memory += ZERO
            memory[i] = value
        }

        val size: Int = memory.size

        fun clone() = Memory(memory)
    }

    companion object {
        private val ZERO = BigInteger.ZERO
        private val ONE = BigInteger.ONE
        private fun Int.toNum(): Num = toBigInteger()

        private operator fun Num.plus(x: Int): Num = this + x.toNum()
    }
}
