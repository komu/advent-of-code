package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun matchsticks(input: String): Int {
    val lines = input.nonEmptyLines()

    return lines.sumBy { it.length } - lines.sumBy { it.decodedLength() }
}

fun matchsticks2(input: String): Int {
    val lines = input.nonEmptyLines()

    return lines.sumBy { it.encodedLength() } - lines.sumBy { it.length }
}

private fun String.decodedLength(): Int {
    require(first() == '"' || last() == '"')

    tailrec fun loop(s: String, count: Int = 0): Int = when {
        s.isEmpty() -> count
        s.startsWith("\\\\") -> loop(s.drop(2), count + 1)
        s.startsWith("\\\"") -> loop(s.drop(2), count + 1)
        s.startsWith("\\x") -> loop(s.drop(4), count + 1)
        s.startsWith("\\") -> error(s)
        else -> loop(s.drop(1), count + 1)
    }

    return loop(substring(1, length - 1))
}

private fun String.encodedLength() = 2 + sumBy { if (it == '"' || it == '\\') 2 else 1 }
