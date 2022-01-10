package komu.adventofcode.aoc2019

import komu.adventofcode.utils.nonEmptyLines

private fun fuel(mass: Int): Int =
    (mass / 3) - 2

private fun fuel2(mass: Int): Int {
    var total = 0
    var fuelNeeded = fuel(mass)

    while (fuelNeeded > 0) {
        total += fuelNeeded
        fuelNeeded = fuel(fuelNeeded)
    }

    return total
}

fun theTyrannyOfTheRocketEquation(input: String) =
    input.nonEmptyLines().map { it.toInt() }.sumBy { fuel(it) }

fun theTyrannyOfTheRocketEquation2(input: String) =
    input.nonEmptyLines().map { it.toInt() }.sumBy { fuel2(it) }
