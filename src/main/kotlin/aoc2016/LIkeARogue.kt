package komu.adventofcode.aoc2016

import komu.adventofcode.utils.countOccurrences

fun likeARogue(input: String, rowCount: Int): Int {
    var row = input.trim().toCharArray()

    var result = row.countOccurrences('.')

    repeat(rowCount - 1) {
        row = nextRow(row)
        result += row.countOccurrences('.')
    }

    return result
}

private fun nextRow(row: CharArray) = CharArray(row.size) { i ->
    determineTrap(row.getOrNull(i - 1) ?: '.', row[i], row.getOrNull(i + 1) ?: '.')
}

private fun determineTrap(left: Char, center: Char, right: Char): Char = when {
    left == '^' && center == '^' && right == '.' -> '^'
    left == '.' && center == '^' && right == '^' -> '^'
    left == '^' && center == '.' && right == '.' -> '^'
    left == '.' && center == '.' && right == '^' -> '^'
    else -> '.'
}
