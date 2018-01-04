package komu.adventofcode

import org.intellij.lang.annotations.Language
import java.util.*

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

val String.hexBits get() = flatMap { it.hexBits }

private val Char.hexBits: List<Boolean>
    get() {
        val value = HEX_CHARS.indexOf(this.toLowerCase())
        return Array(4) { i -> ((value shr (3 - i)) and 1) != 0 }.asList()
    }

private val classLoader = object {}.javaClass.classLoader

fun readTestInputLines(path: String): List<String> {
    val input = classLoader.getResourceAsStream(path) ?: error("could not open '$path'")
    return input.use { it.reader().readLines() }
}

fun readTestInput(@Language("file-reference") path: String): String {
    val input = classLoader.getResourceAsStream(path.removePrefix("/")) ?: error("could not open '$path'")
    return input.use { it.reader().readText() }
}

fun readIntGrid(path: String): List<List<Int>> =
    readTestInput(path).linesToIntGrid()

data class Point(val x: Int, val y: Int) {
    operator fun plus(d: Direction) = Point(x + d.dx, y + d.dy)

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
