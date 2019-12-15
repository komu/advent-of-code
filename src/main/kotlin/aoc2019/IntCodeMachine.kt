package aoc2019

import komu.adventofcode.utils.pow10
import java.math.BigInteger
import java.util.concurrent.LinkedBlockingDeque

private typealias Num = BigInteger

open class IntCodeMachine private constructor(
    val memory: Memory,
    private var ip: Num = ZERO,
    private var relativeBase: Num = ZERO,
    running: Boolean = true) {

    var running = running
        private set
    private val input = LinkedBlockingDeque<Num>()
    private val output = LinkedBlockingDeque<Num>()

    constructor(input: String) : this(Memory(input.trim().split(",").map { it.toBigInteger() }))

    var readInput: () -> BigInteger = { this.input.takeFirst() }

    fun peekFirstOutput(): Num =
        output.first

    fun sendInput(vararg values: Int) {
        for (value in values.map { it.toNum() }.toTypedArray())
            input.addLast(value)
    }

    val outputSize: Int
        get() = output.size

    fun readNext(): Num =
        output.take()

    fun outputToList(): List<Num> =
        output.toList()

    fun clone() = IntCodeMachine(memory.clone(), ip, relativeBase, running)

    fun run() {
        while (running)
            tick()
    }

    fun tick() {
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
                memory[absoluteAddress(1)] = readInput()
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
                running = false
            else ->
                error("unknown code $opcode")
        }
    }

    var writeOutput: (Num) -> Unit = { value ->
        output.put(value)
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

    class Memory(initial: List<Num>) {
        private val memory = initial.toMutableList()

        operator fun get(i: Num): Num =
            memory.getOrNull(i.toInt()) ?: ZERO

        operator fun get(i: Int): Num =
            get(i.toNum())

        operator fun set(index: Num, value: Num) {
            val i = index.toInt()
            while (i >= memory.size)
                memory += ZERO
            memory[i] = value
        }

        operator fun set(index: Int, value: Int) {
            set(index.toNum(), value.toNum())
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
