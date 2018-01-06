package komu.adventofcode.aoc2017

import komu.adventofcode.utils.lineToInts

fun memoryReallocation(input: String): Pair<Int,Int> =
    memoryReallocation(input.lineToInts())

fun memoryReallocation(offsets: List<Int>): Pair<Int,Int> {
    val seen = mutableMapOf<List<Int>, Int>()

    var state = offsets
    var steps = 0

    while (true) {
        val start = seen[state]
        if (start != null)
            return Pair(steps, steps - start)

        seen[state] = steps
        state = reallocate(state)
        steps++
    }
}

private fun reallocate(state: List<Int>): List<Int> {
    val maxIndex = state.indices.maxBy { state[it] }!!
    var slots = state[maxIndex]

    val result = state.toMutableList()
    result[maxIndex] = 0

    var i = maxIndex + 1
    while (slots-- > 0)
        result[i++ % result.size]++

    return result
}
