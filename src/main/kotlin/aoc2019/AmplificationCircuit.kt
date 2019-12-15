package aoc2019

import komu.adventofcode.utils.permutations
import java.util.concurrent.LinkedBlockingDeque
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
                amp.outputToList().first().toInt()
            }
        }
        .max()!!
}

fun amplificationCircuit2(input: String): Int {
    val machine = IntCodeMachine(input)

    return listOf(5, 6, 7, 8, 9).permutations().map { phases ->
        val inputs = phases.map { phase -> LinkedBlockingDeque<Int>(listOf(phase)) }
        var lastOutput = 0
        val amps = inputs.mapIndexed { i, input ->
            val clone = machine.clone()
            clone.readInput = { input.takeFirst().toBigInteger() }
            clone.writeOutput = {
                inputs[(i + 1) % inputs.size].addLast(it.toInt())
                if (i == inputs.size -1)
                    lastOutput = it.toInt()
            }
            clone
        }

        inputs[0].addLast(0)

        for (t in amps.map { thread { it.run() } })
            t.join()

        lastOutput
    }.max()!!
}


