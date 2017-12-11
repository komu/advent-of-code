package komu.adventofcode

fun String.splitBySpace() =
    split(Regex("""\s+"""))

fun String.lineToInts(): List<Int> =
    splitBySpace().map { it.toInt() }

fun String.linesToIntGrid(): List<List<Int>> =
    lines().map(String::lineToInts)
