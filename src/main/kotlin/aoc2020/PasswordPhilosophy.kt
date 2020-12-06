package aoc2020

import komu.adventofcode.utils.countOccurrences

private val passwordPattern = Regex("""(\d+)-(\d+) (.): (.+)""")

fun passwordPhilosophy(passwords: List<String>) =
    passwords.count {
        val (_, min, max, letter, password) = passwordPattern.matchEntire(it)?.groupValues
            ?: error("invalid input: '$it'")
        password.countOccurrences(letter[0]) in min.toInt()..max.toInt()
    }

fun passwordPhilosophy2(passwords: List<String>) =
    passwords.count { isValidPassword2(it) }

private fun isValidPassword2(s: String): Boolean {
    val (_, p1, p2, letter, password) = passwordPattern.matchEntire(s)?.groupValues ?: error("invalid input: '$s'")

    return (password[p1.toInt() - 1] == letter[0]) xor (password[p2.toInt() - 1] == letter[0])
}
