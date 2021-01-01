package aoc2020

import komu.adventofcode.utils.nonEmptyLines

fun dockingData1(input: String): Long {
    val memory = mutableMapOf<Long, Long>()

    for ((mask, assignments) in parseDockingData(input))
        for ((address, value) in assignments)
            memory[address] = applyMask(value, mask)

    return memory.values.sum()
}

fun dockingData2(input: String): Long {
    val memory = mutableMapOf<Long, Long>()

    for ((mask, assignments) in parseDockingData(input))
        for ((address, value) in assignments)
            for (realAddress in applyMask2(address, mask))
                memory[realAddress] = value

    return memory.values.sum()
}

private fun parseDockingData(input: String): List<Pair<String, List<Pair<Long, Long>>>> {
    val assignRegex = Regex("""mem\[(.+)] = (.+)""")
    val result = mutableListOf<Pair<String, List<Pair<Long, Long>>>>()
    val current = mutableListOf<Pair<Long, Long>>()
    var mask = ""

    fun flush() {
        if (current.isNotEmpty()) {
            result += Pair(mask, current.toList())
            current.clear()
        }
    }

    for (line in input.nonEmptyLines()) {
        if (line.startsWith("mask = ")) {
            flush()
            mask = line.removePrefix("mask = ")
        } else {
            val (_, address, value) = assignRegex.matchEntire(line)?.groupValues ?: error("no match for '$line'")
            current += Pair(address.toLong(), value.toLong())
        }
    }

    flush()

    return result
}

private fun applyMask(value: Long, mask: String): Long {
    fun recurse(mask: String, acc: Long): Long =
        if (mask.isNotEmpty()) {
            val bit = when (mask[0]) {
                'X' -> (value shr mask.length - 1) and 1
                '1' -> 1
                '0' -> 0
                else -> error("invalid mask char")
            }

            recurse(mask.drop(1), (acc shl 1) or bit)
        } else
            acc

    return recurse(mask, 0)
}

private fun applyMask2(value: Long, mask: String): List<Long> {
    fun recurse(mask: String, acc: Long): List<Long> =
        if (mask.isNotEmpty()) {
            val bits = when (mask[0]) {
                '0' -> listOf((value shr mask.length - 1) and 1)
                '1' -> listOf(1L)
                'X' -> listOf(0L, 1L)
                else -> error("invalid mask char")
            }

            bits.flatMap { bit -> recurse(mask.drop(1), (acc shl 1) or bit) }

        } else
            listOf(acc)

    return recurse(mask, 0)
}
