package komu.adventofcode.aoc2015

import komu.adventofcode.utils.powerList

fun noSuchThingAsTooMuch1(total: Int, containers: List<Int>): Int =
    containers.powerList().count { it.sum() == total }

fun noSuchThingAsTooMuch2(total: Int, containers: List<Int>): Int {
    val selections = containers.powerList().filter { it.sum() == total }
    val minimum = selections.minOf { it.size }

    return selections.count { it.size == minimum }
}


