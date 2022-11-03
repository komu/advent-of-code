package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines

fun ipv7Part1(input: String) =
    input.nonEmptyLines().count { it.supportsTls() }

fun ipv7Part2(input: String) =
    input.nonEmptyLines().count { it.supportsSsl() }

private fun String.supportsTls(): Boolean {
    val (supernet, hypernet) = parseAddressSequences(this)

    return hypernet.none { it.hasAbba() } && supernet.any { it.hasAbba() }
}

private fun String.supportsSsl(): Boolean {
    val (supernet, hypernet) = parseAddressSequences(this)

    return supernet
        .flatMap { it.findAbas() }
        .map { it.abaToBab() }
        .any { bab -> hypernet.any { bab in it } }
}

private fun String.hasAbba(): Boolean =
    windowed(4, 1).any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }

private fun String.findAbas(): List<String> =
    windowed(3, 1).filter { it[0] == it[2] && it[0] != it[1] }

private fun String.abaToBab(): String =
    "${this[1]}${this[0]}${this[1]}"

private fun parseAddressSequences(s: String): Pair<List<String>, List<String>> {
    val supernet = mutableListOf<String>()
    val hypernet = mutableListOf<String>()

    val current = StringBuilder()
    for (c in s) {
        when (c) {
            '[' -> {
                if (current.isNotEmpty())
                    supernet += current.toString()
                current.setLength(0)
            }
            ']' -> {
                if (current.isNotEmpty())
                    hypernet += current.toString()
                current.setLength(0)
            }
            else -> {
                current.append(c)
            }
        }
    }

    if (current.isNotEmpty())
        supernet += current.toString()

    return Pair(supernet, hypernet)
}