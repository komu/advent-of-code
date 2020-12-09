package aoc2020

import komu.adventofcode.utils.choosePairs

fun encodingError1(preambleLength: Int, data: List<Long>): Long {
    for (window in data.windowed(preambleLength + 1)) {
        val preamble = window.subList(0, preambleLength)
        val value = window.last()

        if (!isValid(preamble, value))
            return value
    }
    error("no result")
}

fun encodingError2(preambleLength: Int, data: List<Long>): Long {
    val invalidNumber = encodingError1(preambleLength, data)

    for (min in data.indices)
        for (max in min + 1..data.size) {
            val range = data.subList(min, max)
            if (range.sum() == invalidNumber)
                return range.minOrNull()!! + range.maxOrNull()!!
        }

    error("no result")
}

private fun isValid(preamble: List<Long>, value: Long) =
    preamble.choosePairs().any { (x, y) -> x != y && x + y == value }
