package aoc2020

import komu.adventofcode.utils.nonEmptyLines

fun crabCombat1(s: String): Int {
    val (deck1, deck2) = parseDecks(s)

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val top1 = deck1.removeAt(0)
        val top2 = deck2.removeAt(0)

        if (top1 > top2) {
            deck1.add(top1)
            deck1.add(top2)
        } else {
            deck2.add(top2)
            deck2.add(top1)
        }
    }

    val winner = deck1.takeIf { it.isNotEmpty() } ?: deck2

    return score(winner)
}

fun crabCombat2(s: String): Int {
    val (deck1, deck2) = parseDecks(s)
    return score(recursiveCombat(deck1, deck2).winningDeck)
}

private fun recursiveCombat(d1: List<Int>, d2: List<Int>): GameResult {
    val seen = mutableSetOf<Pair<List<Int>, List<Int>>>()

    val deck1 = d1.toMutableList()
    val deck2 = d2.toMutableList()

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        if (!seen.add(Pair(deck1.toList(), deck2.toList())))
            return GameResult(true, deck1)

        val top1 = deck1.removeAt(0)
        val top2 = deck2.removeAt(0)

        val winner1 = if (deck1.size >= top1 && deck2.size >= top2)
            recursiveCombat(deck1.take(top1), deck2.take(top2)).winner1
        else
            top1 > top2

        if (winner1) {
            deck1.add(top1)
            deck1.add(top2)
        } else {
            deck2.add(top2)
            deck2.add(top1)
        }
    }

    val winner1 = deck1.isNotEmpty()
    return GameResult(winner1, if (winner1) deck1 else deck2)
}

private class GameResult(val winner1: Boolean, val winningDeck: List<Int>)

private fun score(winner: List<Int>) =
    winner.asReversed().mapIndexed { i, n -> (i + 1) * n }.sum()

private fun parseDecks(s: String): Pair<MutableList<Int>, MutableList<Int>> {
    val (d1, d2) = s.split("\n\n").map { p -> p.nonEmptyLines().drop(1).map { it.toInt() }.toMutableList() }
    return Pair(d1, d2)
}
