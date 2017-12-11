package komu.adventofcode

fun memoryReallocation(input: String): Int =
    memoryReallocation(input.lineToInts())

fun memoryReallocation(offsets: List<Int>): Int {
    val seen = mutableSetOf<List<Int>>()

    var state = offsets
    var steps = 0
    while (seen.add(state)) {
        state = reallocate(state)
        steps++
    }

    return steps
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
