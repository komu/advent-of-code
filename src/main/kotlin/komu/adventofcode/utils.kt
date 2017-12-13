package komu.adventofcode

import org.intellij.lang.annotations.Language

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

private const val HEX_CHARS = "0123456789abcdef"

fun List<Int>.octetsToHex(): String = buildString {
    for (octet in this@octetsToHex) {
        append(HEX_CHARS[(octet and 0xF0).ushr(4)])
        append(HEX_CHARS[octet and 0x0F])
    }
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
