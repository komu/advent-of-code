package komu.adventofcode.utils

import org.intellij.lang.annotations.Language
import java.security.MessageDigest
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
    toList().sorted().joinToString("")

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

fun BitSet.add(b: Int): Boolean {
    if (get(b))
        return false

    set(b, true)
    return true
}

private const val HEX_CHARS = "0123456789abcdef"

fun List<Int>.octetsToHex(): String = buildString {
    for (octet in this@octetsToHex) {
        append(HEX_CHARS[(octet and 0xF0).ushr(4)])
        append(HEX_CHARS[octet and 0x0F])
    }
}

fun square(x: Int) = x * x

fun ByteArray.hexEncode(): String =
    map { it.toInt() }.octetsToHex()

val String.hexBits get() = flatMap { it.hexBits }

fun <T> List<T>.contentMatch(at: Int, xs: List<T>): Boolean =
    (at + xs.size <= size) && xs.indices.all { this[at + it] == xs[it] }

private val Char.hexBits: List<Boolean>
    get() {
        val value = HEX_CHARS.indexOf(this.lowercaseChar())
        return Array(4) { i -> ((value shr (3 - i)) and 1) != 0 }.asList()
    }

fun Boolean.toBit(): Int =
    if (this) 1 else 0

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
    operator fun plus(d: Dir) = towards(d, 1)

    val manhattanDistanceFromOrigin: Int
        get() = manhattanDistance(ORIGIN)

    fun manhattanDistance(p: Point): Int =
        abs(x - p.x) + abs(y - p.y)

    fun towards(d: Direction, distance: Int = 1) = Point(x + distance * d.dx, y + distance * d.dy)
    fun towards(d: Dir, distance: Int = 1) = Point(x + distance * d.dx, y + distance * d.dy)

    fun rotateClockwise() =
        Point(y, -x)

    fun rotateCounterClockwise() =
        Point(-y, x)

    val neighbors: List<Point>
        get() = Direction.values().map { this + it }

    val allNeighbors: List<Point>
        get() = buildList {
            for (dy in -1..1)
                for (dx in -1..1)
                    if (dx != 0 || dy != 0)
                        add(Point(x + dx, y + dy))
        }

    override fun toString() = "<$x, $y>"

    fun directionOfNeighbor(neighbor: Point): Direction =
        Direction.values().find { this + it == neighbor } ?: error("$neighbor is not a neighbor of $this")

    fun isAdjacentTo(p: Point): Boolean {
        val dx = abs(x - p.x)
        val dy = abs(y - p.y)
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1)
    }

    companion object {
        val ORIGIN = Point(0, 0)

        val readingOrderComparator = compareBy<Point> { it.y }.thenBy { it.x }
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

data class Dir(val dx: Int, val dy: Int) {

    companion object {
        val all = listOf(
            Dir(0, 1), Dir(-1, 1),
            Dir(-1, 0), Dir(-1, -1),
            Dir(0, -1), Dir(1, -1),
            Dir(1, 0), Dir(1, 1)
        )

        val NORTH = Dir(0, 1)
        val SOUTH = Dir(0, -1)
        val EAST = Dir(1, 0)
        val WEST = Dir(-1, 0)
    }
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

fun String.countOccurrences(element: Char): Int =
    count { it == element }

fun CharArray.countOccurrences(element: Char): Int =
    count { it == element }

fun <T> List<T>.choosePairs(): List<Pair<T, T>> =
    withIndex().flatMap { (index, a) -> subList(index + 1, size).map { b -> a to b } }

fun lcm(a: Long, b: Long) =
    a * (b / gcd(a, b))

fun lcm3(a: Long, b: Long, c: Long): Long =
    lcm(a, lcm(b, c))

fun gcd(x: Long, y: Long): Long {
    var a = x
    var b = y

    while (a != b) {
        if (a > b)
            a -= b
        else
            b -= a
    }
    return a
}

fun Iterable<Long>.product(): Long =
    fold(1L) { x, y -> x * y }

@JvmName("ULongProduct")
fun Iterable<ULong>.product(): ULong =
    fold(1UL) { x, y -> x * y }

inline fun <T> Iterable<T>.sumByLong(f: (T) -> Long): Long =
    fold(0L) { s, x -> s + f(x) }

fun <T> checkEqual(a: T, b: T) {
    if (a != b)
        error("$a != $b")
}

fun <T> Collection<T>.powerset(): Set<Set<T>> = when {
    isEmpty() -> setOf(setOf())
    else -> drop(1).powerset().let { it + it.map { it + first() } }
}

fun <T> Collection<T>.powerList(): List<List<T>> = when {
    isEmpty() -> listOf(listOf())
    else -> drop(1).powerList().let { it + it.map { it + first() } }
}

fun multiplicativeInverse(a: Long, n: Long): Long {
    var t = 0L
    var newt = 1L
    var r = n
    var newr = a

    while (newr != 0L) {
        val quotient = r / newr

        val tt = t - quotient * newt
        val rr = r - quotient * newr
        t = newt
        newt = tt
        r = newr
        newr = rr
    }

    if (r > 1)
        error("$a is not invertible mod $n")

    if (t < 0)
        t += n

    return t
}

fun hexEncodedMd5Hash(s: String) =
    MessageDigest.getInstance("MD5").digest(s.toByteArray()).hexEncode()
