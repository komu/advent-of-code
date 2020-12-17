package aoc2020

fun adapterArray1(adapters: List<Int>): Int {
    var ones = 0
    var threes = 0

    for ((a, b) in adapters.toAdapterChain().zipWithNext()) {
        when (b - a) {
            1 -> ones++
            3 -> threes++
        }
    }

    return ones * threes
}

fun adapterArray2(adapters: List<Int>): Long {
    val chain = adapters.toAdapterChain()

    val lengths = LongArray(chain.size) { 0 }
    lengths[lengths.lastIndex] = 1

    for (i in chain.indices.reversed().drop(1)) {
        var sum = 0L
        for (j in (i + 1)..chain.lastIndex) {
            if (chain[j] - chain[i] <= 3)
                sum += lengths[j]
            else
                break
        }
        lengths[i] = sum
    }

    return lengths[0]
}

private fun List<Int>.toAdapterChain() = listOf(0) + sorted() + (maxOrNull()!! + 3)
