package aoc2019

import kotlin.math.absoluteValue

fun flawedFrequencyTransmission1(input: String): String {
    var s = input.map { it - '0' }.toIntArray()

    repeat(100) {
        s = phase(s)
    }

    return s.take(8).joinToString("")
}

fun flawedFrequencyTransmission2(input: String): String {
    val offset = input.take(7).toInt()
    val data = input.repeat(10_000).map { (it - '0') }.drop(offset).toIntArray()

    repeat(100) {
        var sum = 0
        for (index in data.indices.reversed()) {
            sum += data[index]
            data[index] = sum % 10
        }
    }

    return data.joinToString("").take(8)
}

private fun phase(s: IntArray): IntArray =
    IntArray(s.size) { i -> step(s, i + 1) }

private fun step(s: IntArray, position: Int): Int {
    var sum = 0

    for (i in position - 1 until s.size step position * 4)
        for (j in i until (i + position).coerceAtMost(s.size))
            sum += s[j]

    for (i in position * 3 - 1 until s.size step position * 4)
        for (j in i until (i + position).coerceAtMost(s.size))
            sum -= s[j]

    return sum.absoluteValue % 10
}
