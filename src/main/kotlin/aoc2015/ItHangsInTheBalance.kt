package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.product

fun itHangsInTheBalance1(input: String): Long {
    val weights = input.nonEmptyLines().map { it.toLong() }.reversed()
    val expectedWeight = weights.sum() / 3

    var bestSize = Integer.MAX_VALUE
    var bestEntanglement = Long.MAX_VALUE
    for (a in candidates(weights, IndexSet.empty, expectedWeight, allowedSize = 1..weights.size / 3, maxCount = 1)) {
        val maxSize = weights.size - 2 * a.size
        if (hasMatch(weights, a, expectedWeight, allowedSize = a.size..maxSize)) {
            if (a.size <= bestSize && weights.withIndices(a).product() < bestEntanglement) {
                bestSize = a.size
                bestEntanglement = weights.withIndices(a).product()
            }
        }
    }

    return bestEntanglement
}

fun itHangsInTheBalance2(input: String): Long {
    val weights = input.nonEmptyLines().map { it.toLong() }.reversed()
    val expectedWeight = weights.sum() / 4

    var bestSize = Integer.MAX_VALUE
    var bestEntanglement = Long.MAX_VALUE
    for (a in candidates(weights, IndexSet.empty, expectedWeight, allowedSize = 0..weights.size / 4, maxCount = 100)) {
        val allowedSize = a.size..weights.size - 3 * a.size
        for (b in candidates(weights, a, expectedWeight, allowedSize, maxCount = 1)) {
            if (hasMatch(weights, b, expectedWeight, allowedSize)) {
                if (a.size <= bestSize && weights.withIndices(a).product() < bestEntanglement) {
                    bestSize = a.size
                    bestEntanglement = weights.withIndices(a).product()
                }
            }
        }
    }

    return bestEntanglement
}

private fun candidates(
    weights: List<Long>,
    taken: IndexSet,
    expectedWeight: Long,
    allowedSize: IntRange,
    maxCount: Int
): Set<IndexSet> {
    val result = mutableSetOf<IndexSet>()
    var count = 0
    fun add(taken: IndexSet) {
        count++
        result += taken
    }

    fun recurse(taken: IndexSet, remainingSum: Long, items: Int) {
        if (remainingSum == 0L && items >= allowedSize.first)
            add(taken)

        val remainingItems = allowedSize.last - items
        if (remainingItems > 1 && count < maxCount) {
            for ((i, w) in weights.withIndex()) {
                if (w <= remainingSum && i !in taken)
                    recurse(taken + i, remainingSum - w, items + 1)
            }
        }
    }

    recurse(taken, expectedWeight, 0)
    return result
}

private fun hasMatch(weights: List<Long>, taken: IndexSet, expectedWeight: Long, allowedSize: IntRange): Boolean {
    fun recurse(taken: IndexSet, remainingSum: Long, items: Int): Boolean {
        if (remainingSum == 0L)
            return items in allowedSize

        if (items > allowedSize.last)
            return false

        for ((i, w) in weights.withIndex()) {
            if (w <= remainingSum && i !in taken)
                if (recurse(taken + i, remainingSum - w, items + 1))
                    return true
        }
        return false
    }

    return recurse(taken, expectedWeight, 0)
}

@JvmInline
private value class IndexSet(private val bits: Int) {

    operator fun contains(index: Int) = (bits and (1 shl index)) != 0

    operator fun plus(index: Int) = IndexSet(bits or (1 shl index))
    operator fun minus(index: Int) = IndexSet(bits and (1 shl index).inv())
    operator fun minus(rhs: IndexSet) = IndexSet(bits and rhs.bits.inv())

    override fun toString(): String =
        (0..31).filter { it in this }.toString()

    val size: Int
        get() = Integer.bitCount(bits)

    companion object {
        val empty = IndexSet(0)
    }
}

private fun <T> List<T>.withIndices(indexSet: IndexSet): List<T> =
    withIndex().filter { it.index in indexSet }.map { it.value }

