package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun antSue1(input: String): Int =
    Sue.parseAll(input).find { it.matchesKnown() }?.number ?: error("no match")

fun antSue2(input: String): Int =
    Sue.parseAll(input).find { it.matchesKnown2() }?.number ?: error("no match")

private class Sue(val number: Int, val things: Map<String, Int>) {

    fun matchesKnown() =
        isEqual("children", 3) &&
                isEqual("cats", 7) &&
                isEqual("samoyeds", 2) &&
                isEqual("pomeranians", 3) &&
                isEqual("akitas", 0) &&
                isEqual("vizslas", 0) &&
                isEqual("goldfish", 5) &&
                isEqual("trees", 3) &&
                isEqual("cars", 2) &&
                isEqual("perfumes", 1)

    fun matchesKnown2() = isEqual("children", 3) &&
            isEqual("samoyeds", 2) &&
            isEqual("akitas", 0) &&
            isEqual("vizslas", 0) &&
            isEqual("cars", 2) &&
            isEqual("perfumes", 1) &&
            isGreater("cats", 7) &&
            isGreater("trees", 3) &&
            isLess("pomeranians", 3) &&
            isLess("goldfish", 5)

    private fun isEqual(key: String, expected: Int) =
        (things[key] ?: expected) == expected

    private fun isGreater(key: String, expected: Int) =
        (things[key] ?: (expected + 1)) > expected

    private fun isLess(key: String, expected: Int) =
        (things[key] ?: (expected - 1)) < expected

    companion object {
        private val regex = Regex("""Sue (\d+): (.+)""")

        fun parseAll(input: String) =
            input.nonEmptyLines().map { parse(it) }

        private fun parse(input: String): Sue {
            val (num, things) = regex.matchEntire(input)?.destructured ?: error("invalid Sue '$input'")

            return Sue(num.toInt(), things = things.split(", ").associate {
                val (thing, count) = it.split(": ")
                thing to count.toInt()
            })
        }
    }
}
