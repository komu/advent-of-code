package aoc2019

import komu.adventofcode.utils.digits

fun secureContainer1(minValue: Int, maxValue: Int): Int =
    (minValue..maxValue).count { isValid1(it) }

fun secureContainer2(minValue: Int, maxValue: Int): Int =
    (minValue..maxValue).count { isValid2(it) }

private fun isValid1(value: Int): Boolean {
    val digits = digits(value)
    return digits.isNonDecreasing() && digits.hasAdjacentDigits()
}

private fun isValid2(value: Int): Boolean {
    val digits = digits(value)
    return digits.isNonDecreasing() && hasSingleMatchingGroup(digits)
}

private fun hasSingleMatchingGroup(digits: List<Int>): Boolean {
    var previous = -1
    var count = 0
    for (digit in digits) {
        if (digit != previous) {
            if (count == 2)
                return true
            count = 1
        } else {
            count++
        }
        previous = digit
    }

    return count == 2
}

private fun List<Int>.hasAdjacentDigits() =
    zipWithNext().any { (a, b) -> a == b }

private fun List<Int>.isNonDecreasing() =
    zipWithNext().all { (a, b) -> a <= b }
