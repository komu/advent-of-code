package aoc2019

import komu.adventofcode.utils.permutations
import kotlin.concurrent.thread

fun amplificationCircuit1(input: String): Int {
    val machine = IntCodeMachine(input)
    return listOf(0, 1, 2, 3, 4)
        .permutations()
        .map { phases ->
            phases.fold(0) { v, phase ->
                val amp = machine.clone()
                amp.sendInput(phase, v)
                amp.run()
                amp.peekFirstOutput()
            }
        }
        .max()!!
}

fun amplificationCircuit2(input: String): Int {
    val machine = IntCodeMachine(input)

    return listOf(5, 6, 7, 8, 9).permutations().map { phases ->
        val amps = phases.map { phase -> machine.clone().apply { sendInput(phase) } }
        val copiers = amps.indices.map { i -> amps[i].copyOutputTo(amps[(i + 1) % amps.size]) }

        amps[0].sendInput(0)

        for (t in amps.map { thread { it.run() } })
            t.join()

        for (t in copiers)
            t.interrupt()

        amps.last().peekLastOutput()
    }.max()!!
}

private fun IntCodeMachine.copyOutputTo(dst: IntCodeMachine): Thread =
    thread {
        try {
            while (!Thread.interrupted()) {
                dst.sendInput(readNext())
            }
        } catch (e: InterruptedException) {
        }
    }


