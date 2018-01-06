package komu.adventofcode.aoc2015

fun countNiceStrings(input: String): Int =
        input.lines().count { it.isNice() }

fun countNiceStrings2(input: String): Int =
        input.lines().count { it.isNice2() }

private fun String.isNice(): Boolean {
    val hasThreeVowels = count { it in "aeiou" } >= 3
    val hasLetterTwiceInARow = windowed(2).any { it[0] == it[1] }
    val hasForbiddenSequence = listOf("ab", "cd", "pq", "xy").any { it in this }

    return hasThreeVowels && hasLetterTwiceInARow && !hasForbiddenSequence
}

private fun String.isNice2(): Boolean {
    val hasRepeatingPair = indices.toList().dropLast(2).any { indexOf(substring(it, it + 2), startIndex = it + 2) != -1 }
    val hasProperLetterRepeat = windowed(3).any { it[0] == it[2] }

    return hasRepeatingPair && hasProperLetterRepeat
}
