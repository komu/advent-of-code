package komu.adventofcode.aoc2020

import komu.adventofcode.utils.choosePairs

fun reportRepair(numbers: List<Int>): Int =
    numbers.choosePairs().find { it.first + it.second == 2020 }?.let { it.first * it.second }!!

fun reportRepair2(numbers: List<Int>): Int {
    for (a in numbers)
        for (b in numbers)
            for (c in numbers)
                if (a + b + c == 2020)
                    return a * b * c
    error("no result")
}
