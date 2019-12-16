package aoc2019

import komu.adventofcode.utils.permutations
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

fun amplificationCircuit1(input: String): Long {
    val machine = IntCodeMachine(input)
    return listOf(0L, 1L, 2L, 3L, 4L)
        .permutations()
        .map { phases ->
            phases.fold(0L) { v, phase ->
                val amp = machine.clone()
                amp.sendInput(phase, v)
                amp.run()
                amp.outputToList().first()
            }
        }
        .max()!!
}

fun amplificationCircuit2(input: String): Int {
    val machine = IntCodeMachine(input)

    return listOf(5L, 6L, 7L, 8L, 9L).permutations().map { phases ->
        val inputs = phases.map { phase -> LinkedBlockingDeque<Long>(listOf(phase)) }
        var lastOutput = 0
        val amps = inputs.mapIndexed { i, input ->
            val clone = machine.clone()
            clone.readInput = { input.takeFirst() }
            clone.writeOutput = {
                inputs[(i + 1) % inputs.size].addLast(it)
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


