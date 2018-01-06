package komu.adventofcode.aoc2017

import komu.adventofcode.utils.nonEmptyLines

fun fractalArt(input: String, iterations: Int): Int {
    val rules = RuleBook.parse(input)

    var block = Block(listOf(".#.", "..#", "###"))

    repeat(iterations) {
        block = block.enhance(rules)
    }

    return block.pixelsOnCount
}

private class RuleBook(private val rules: List<Rule>) {

    fun findReplacement(fingerprint: Fingerprint) =
        rules.find { it.matches(fingerprint) }?.output ?: error("no matching rule for $fingerprint")

    companion object {
        fun parse(input: String) = RuleBook(input.nonEmptyLines().map(Rule.Companion::parse))
    }
}

private class Block private constructor(val size: Int) {

    private val grid = Array(size) { CharArray(size) }

    constructor(lines: List<String>) : this(lines.size) {
        lines.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                grid[y][x] = c
            }
        }
    }

    val pixelsOnCount: Int
        get() = grid.sumBy { it.count { it == '#' } }

    override fun toString() = buildString {
        for (line in grid)
            appendln(String(line))
    }

    fun enhance(rules: RuleBook) = when {
        size % 2 == 0 -> enhance(rules, 2, 3)
        size % 3 == 0 -> enhance(rules, 3, 4)
        else -> error("invalid size $size")
    }

    private fun enhance(rules: RuleBook, mod: Int, newMod: Int): Block {
        val parts = size / mod

        val enhanced = Block(parts * newMod)
        for (y in 0 until parts) {
            for (x in 0 until parts) {
                val fingerprint = fingerprint(x * mod, y * mod, mod)
                val replacement = rules.findReplacement(fingerprint)

                enhanced.copyFrom(x * newMod, y * newMod, replacement)
            }
        }

        return enhanced
    }

    private fun fingerprint(x: Int, y: Int, size: Int) = buildString {
        for (yy in y until y + size)
            for (xx in x until x + size)
                append(grid[yy][xx])
    }

    private fun copyFrom(x: Int, y: Int, block: Block) {
        for (yy in 0 until block.size)
            for (xx in 0 until block.size) {
                val v = block.grid[yy][xx]
                grid[yy + y][xx + x] = v
            }
    }
}

private typealias Fingerprint = String

private class Rule(permutationIndices: List<List<Int>>, private val pattern: String, val output: Block) {

    private val permutations = permutationIndices.map { indices ->
        indices.map { pattern[it] }.joinToString("")
    }.toSet()

    fun matches(fingerprint: Fingerprint) = fingerprint in permutations

    companion object {
        private val permutations2x2 =
            digitLists("0123", "2031", "2310", "1203", "1032", "2301", "3210")

        private val permutations3x3 = run {
            val rots = digitLists("012345678", "630741852", "876543210", "258147036")
            val flips = digitLists("012345678", "210543876", "678345012", "876543210")

            rots.flatMap { rot -> flips.map { flip -> rot.map { flip[it] } } }
        }

        private val regex = Regex("(.+) => (.+)")

        fun parse(input: String): Rule {
            val m = regex.matchEntire(input) ?: error("invalid input '$input'")
            val pattern = m.groupValues[1].split('/').joinToString("")
            val replacement = Block(m.groupValues[2].split('/'))

            return when (pattern.length) {
                4 -> Rule(permutations2x2, pattern, replacement)
                9 -> Rule(permutations3x3, pattern, replacement)
                else -> error("invalid pattern '$pattern'")
            }
        }

        private fun digitLists(vararg s: String): List<List<Int>> =
                s.map { it.map { it.toInt() - '0'.toInt() } }
    }
}

