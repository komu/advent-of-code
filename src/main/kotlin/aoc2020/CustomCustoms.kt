package komu.adventofcode.aoc2020

fun customCustoms1(data: String): Int =
    groupAnswers(data).sumBy { it.reduce { x, y -> x union y }.size }

fun customCustoms2(data: String): Int =
    groupAnswers(data).sumBy { it.reduce { x, y -> x intersect y }.size }

private fun groupAnswers(data: String) =
    data.split("\n\n")
        .map { g -> g.lines().map { it.toCharArray().toSet() } }
