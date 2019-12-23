package aoc2019

import komu.adventofcode.utils.pow10
import java.util.concurrent.LinkedBlockingDeque

open class IntCodeMachine private constructor(
    val memory: Memory,
    private var ip: Long = 0,
    private var relativeBase: Long = 0,
    running: Boolean = true) {

    var running = running
        private set
    private val input = LinkedBlockingDeque<Long>()
    private val output = LinkedBlockingDeque<Long>()

    constructor(input: String) : this(Memory(input.trim().split(",").map { it.toLong() }))

    var readInput: () -> Long = { this.input.takeFirst() }

    fun peekFirstOutput(): Long =
        output.first

    fun sendInput(vararg values: Long) {
        for (value in values)
            input.addLast(value)
    }

    fun writeLine(s: String) {
        for (c in s)
            sendInput(c.toLong())
        sendInput('\n'.toLong())
    }

    val outputSize: Int
        get() = output.size

    fun readNext(): Long =
        output.take()

    fun outputToList(): List<Long> =
        output.toList()

    fun clone() = IntCodeMachine(memory.clone(), ip, relativeBase, running)

    fun run() {
        while (running)
            tick()
    }

    fun sendInputAndWaitForOutput(input: Long): Long {
        readInput = { input }

        return waitForOutput()
    }

    fun waitForOutput(): Long {
        var output: Long? = null
        writeOutput = { reply ->
            output = reply
        }

        while (output == null)
            tick()

        return output!!
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
                if (param(1) != 0L)
                    ip = param(2)
                else
                    ip += 3
            6 ->
                if (param(1) == 0L)
                    ip = param(2)
                else
                    ip += 3
            7 -> {
                memory[absoluteAddress(3)] = if (param(1) < param(2)) 1L else 0L
                ip += 4
            }
            8 -> {
                memory[absoluteAddress(3)] = if (param(1) == param(2)) 1L else 0L
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

    var writeOutput: (Long) -> Unit = { value ->
        output.put(value)
    }

    private fun param(index: Int): Long {
        val op = memory[ip].toInt()
        val mode = (op / pow10(index + 1)) % 10
        return resolve(memory[ip + index], mode)
    }

    private fun absoluteAddress(index: Int): Long {
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

    private fun resolve(value: Long, mode: Int) = when (mode) {
        0 -> memory[value]
        1 -> value
        2 -> memory[relativeBase + value]
        else -> error("invalid addressing mode $mode for op ${memory[ip]}")
    }

    class Memory(initial: List<Long>) {
        private val memory = initial.toMutableList()

        operator fun get(i: Long): Long =
            memory.getOrNull(i.toInt()) ?: 0

        operator fun set(index: Long, value: Long) {
            val i = index.toInt()
            while (i >= memory.size)
                memory += 0
            memory[i] = value
        }

        val size: Int
            get() = memory.size

        fun clone() = Memory(memory)
    }
}
