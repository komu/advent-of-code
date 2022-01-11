package komu.adventofcode.aoc2019

import komu.adventofcode.utils.multiplicativeInverse
import komu.adventofcode.utils.nonEmptyLines

fun slamShuffle(input: String): Long {
    val shuffle = Shuffle.parse(input.nonEmptyLines(), size = 10007)
    return shuffle(2019)
}

fun slamShuffle2(input: String): Long {
    val one = Shuffle.parse(input.nonEmptyLines(), size = 119_315_717_514_047)
    val thousand = 1000 * one
    val million = 1000 * thousand
    val billion = 1000 * million
    val trillion = 1000 * billion

    val repeated = (101 * trillion) + (741 * billion) + (582 * million) + (76 * thousand) + (661 * one)
    val shuffle = repeated.inverse()
    return shuffle(2020)
}

private class Shuffle(
    private val multiplier: ModularNatural,
    private val offset: ModularNatural
) {

    constructor(size: Long, multiplier: Long, offset: Long) : this(
        multiplier = ModularNatural(multiplier, size),
        offset = ModularNatural(offset, size)
    )

    operator fun invoke(index: Long) =
        (multiplier * index + offset).toLong()

    operator fun plus(b: Shuffle) =
        Shuffle(multiplier = multiplier * b.multiplier, offset = b.multiplier * offset + b.offset)

    fun inverse() =
        Shuffle(multiplier = multiplier.multiplicativeInverse, offset = multiplier.multiplicativeInverse * offset.additiveInverse)

    companion object {

        fun deal(size: Long) = Shuffle(size, multiplier = size - 1, offset = size - 1)
        fun cut(size: Long, count: Long) = Shuffle(size, multiplier = 1, offset = (size - count) % size)
        fun deal(size: Long, increment: Long) = Shuffle(size, multiplier = increment, offset = 0)

        fun parse(input: List<String>, size: Long) =
            input.map { parse(it, size) }.reduce { a, b -> a + b }

        fun parse(input: String, size: Long): Shuffle = when {
            input == "deal into new stack" -> deal(size)
            input.startsWith("cut ") -> cut(size, input.drop(4).toLong())
            input.startsWith("deal with increment ") -> deal(size, input.drop(20).toLong())
            else -> error("invalid input '$input'")
        }
    }
}

private operator fun Int.times(shuffle: Shuffle): Shuffle {
    require(this > 0)
    var result = shuffle
    repeat(this - 1) {
        result += shuffle
    }
    return result
}

private class ModularNatural(private val value: Long, val modulo: Long) {

    init {
        require(value in 0 until modulo)
    }

    fun toLong() = value

    operator fun plus(rhs: ModularNatural) = ModularNatural((value + rhs.value) % modulo, modulo)
    operator fun plus(rhs: Long) = plus(ModularNatural(rhs, modulo))
    operator fun minus(rhs: ModularNatural) = ModularNatural((value - rhs.value + modulo) % modulo, modulo)
    operator fun times(rhs: ModularNatural) = times(rhs.value)
    operator fun times(rhs: Long) = ModularNatural((value.toBigInteger() * rhs.toBigInteger() % modulo.toBigInteger()).toLong(), modulo)

    val additiveInverse: ModularNatural
        get() = ModularNatural((-value + modulo) % modulo, modulo)

    val multiplicativeInverse: ModularNatural
        get() = ModularNatural(multiplicativeInverse(value, modulo), modulo)
}
