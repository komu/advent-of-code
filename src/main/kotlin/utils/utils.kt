package komu.adventofcode.utils

import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.math.abs

fun String.splitBySpace() =
    split(Regex("""\s+"""))

fun String.lineToInts(): List<Int> =
    splitBySpace().map { it.toInt() }

fun String.commaSeparatedInts(): List<Int> =
    split(Regex(""",\s*""")).map { it.toInt() }

fun String.linesToIntGrid(): List<List<Int>> =
    nonEmptyLines().map(String::lineToInts)

fun String.sorted(): String =
    toList().sorted().joinToString()

fun List<String>.withoutEmptyLines() =
    filter(String::isNotEmpty)

fun String.nonEmptyLines() =
    lines().withoutEmptyLines()

fun IntArray.swap(i: Int, j: Int) {
    val tmp = this[i]
    this[i] = this[j]
    this[j] = tmp
}

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    Collections.swap(this, i, j)
}

fun <T> MutableList<T>.rotate(distance: Int) {
    Collections.rotate(this, distance)
}

private const val HEX_CHARS = "0123456789abcdef"

fun List<Int>.octetsToHex(): String = buildString {
    for (octet in this@octetsToHex) {
        append(HEX_CHARS[(octet and 0xF0).ushr(4)])
        append(HEX_CHARS[octet and 0x0F])
    }
}

fun ByteArray.hexEncode(): String =
    map { it.toInt() }.octetsToHex()

val String.hexBits get() = flatMap { it.hexBits }

private val Char.hexBits: List<Boolean>
    get() {
        val value = HEX_CHARS.indexOf(this.toLowerCase())
        return Array(4) { i -> ((value shr (3 - i)) and 1) != 0 }.asList()
    }

private val classLoader = object {}.javaClass.classLoader

fun readTestInput(@Language("file-reference") path: String): String {
    val input = classLoader.getResourceAsStream(path.removePrefix("/")) ?: error("could not open '$path'")
    return input.use { it.reader().readText() }
}

fun String.skipChars(offset: Int, step: Int): List<Char> =
    drop(offset).windowed(size = 1, step = step).map { it.single() }

fun readIntGrid(path: String): List<List<Int>> =
    readTestInput(path).linesToIntGrid()

fun digits(value: Int): List<Int> {
    val result = mutableListOf<Int>()

    var v = value
    while (v != 0) {
        result += (v % 10)
        v /= 10
    }

    result.reverse()
    return result
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(d: Direction) = towards(d, 1)

    val manhattanDistanceFromOrigin: Int
        get() = abs(x + y)

    fun towards(d: Direction, distance: Int) = Point(x + distance * d.dx, y + distance * d.dy)

    override fun toString() = "<$x, $y>"

    companion object {
        val ORIGIN = Point(0, 0)
    }
}

enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    val left: Direction
        get() = values()[(ordinal + 3) % 4]

    val right: Direction
        get() = values()[(ordinal + 1) % 4]

    val opposite: Direction
        get() = values()[(ordinal + 2) % 4]

    fun isOpposite(d: Direction) = dx == -d.dx && dy == -d.dy
}

fun pow10(n: Int): Int {
    var result = 1
    repeat(n) {
        result *= 10
    }
    return result
}

fun <T> List<T>.permutations(): List<List<T>> {
    if (size == 0) return emptyList()
    if (size == 1) return listOf(this)
    val result = mutableListOf<List<T>>()
    val toInsert = first()
    for (smaller in subList(1, size).permutations()) {
        for (i in 0..smaller.size) {
            val permutation = ArrayList<T>(smaller.size)

            permutation += smaller.subList(0, i)
            permutation += toInsert
            permutation += smaller.subList(i, smaller.size)
            result += permutation
        }
    }
    return result
}
