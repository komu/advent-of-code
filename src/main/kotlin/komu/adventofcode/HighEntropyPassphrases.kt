package komu.adventofcode

fun countValidPassphrases(input: List<String>) =
    input.count(::isValidPassphrase)

fun countValidPassphrases2(input: List<String>) =
    input.count(::isValidPassphrase2)

fun isValidPassphrase(phrase: String): Boolean {
    val words = phrase.splitBySpace()
    return words.size == words.distinct().size
}

fun isValidPassphrase2(phrase: String): Boolean {
    val words = phrase.splitBySpace().map { it.sorted() }
    return words.size == words.distinct().size
}
