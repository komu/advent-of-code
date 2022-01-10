package komu.adventofcode.aoc2019

import java.util.concurrent.LinkedBlockingDeque

fun categorySix(input: String): Long {
    val inputs = List(50) { LinkedBlockingDeque(listOf(it.toLong())) }
    val machines = List(50) { IntCodeMachine(input) }

    var result: Long? = null

    for ((i, machine) in machines.withIndex()) {
        val buffer = mutableListOf<Long>()

        machine.readInput = {
            inputs[i].pollFirst() ?: -1
        }

        machine.writeOutput = { output ->
            buffer += output
            if (buffer.size == 3) {
                val (dest, x, y) = buffer
                val address = dest.toInt()

                buffer.clear()

                if (address == 255) {
                    result = y
                } else {
                    inputs[address].addAll(listOf(x, y))
                }
            }
        }
    }

    while (result == null)
        for (machine in machines)
            if (machine.running)
                machine.tick()

    return result!!
}

fun categorySix2(input: String): Long {
    val inputs = List(50) { LinkedBlockingDeque(listOf(it.toLong())) }
    val polling = MutableList(50) { false }
    val machines = List(50) { IntCodeMachine(input) }

    var nat: Pair<Long, Long>? = null
    var previousY: Long? = null
    var result: Long? = null

    for ((i, machine) in machines.withIndex()) {
        val buffer = mutableListOf<Long>()

        machine.readInput = {
            val value = inputs[i].pollFirst()
            if (value != null) {
                polling[i] = false
                value
            } else {
                polling[i] = true

                if (nat != null && polling.all { it }) {
                    val x = nat!!.first
                    val y = nat!!.second
                    if (y == previousY)
                        result = y

                    nat = null
                    previousY = y
                    inputs[0].addAll(listOf(x, y))
                }

                -1
            }
        }

        machine.writeOutput = { output ->
            buffer += output
            if (buffer.size == 3) {
                val (dest, x, y) = buffer
                val address = dest.toInt()

                buffer.clear()

                if (address == 255) {
                    nat = Pair(x, y)
                } else {
                    inputs[address].addAll(listOf(x, y))
                }
            }
        }
    }

    while (result == null)
        for (machine in machines)
            if (machine.running)
                machine.tick()

    return result!!
}

