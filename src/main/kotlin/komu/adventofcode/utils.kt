package komu.adventofcode

fun String.splitBySpace() =
    split(Regex("""\s+"""))

fun String.lineToInts(): List<Int> =
    splitBySpace().map { it.toInt() }

fun String.linesToIntGrid(): List<List<Int>> =
    nonEmptyLines().map(String::lineToInts)

fun String.sorted(): String =
    toList().sorted().joinToString()

fun List<String>.withoutEmptyLines() =
    filter(String::isNotEmpty)

fun String.nonEmptyLines() =
    lines().withoutEmptyLines()

private val classLoader = object {}.javaClass.classLoader

fun readTestInputLines(path: String): List<String> {
    val input = classLoader.getResourceAsStream(path) ?: error("could not open '$path'")
    return input.use { it.reader().readLines() }
}

fun readTestInput(path: String): String {
    val input = classLoader.getResourceAsStream(path) ?: error("could not open '$path'")
    return input.use { it.reader().readText() }
}

fun readIntGrid(path: String): List<List<Int>> =
    readTestInput(path).linesToIntGrid()
