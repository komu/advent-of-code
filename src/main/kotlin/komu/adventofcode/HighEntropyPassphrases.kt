package komu.adventofcode

fun countValidPassphrases(input: List<String>) =
    input.count(::isValidPassphrase)

fun isValidPassphrase(phrase: String): Boolean {
    val words = phrase.splitBySpace()
    return words.size == words.distinct().size
}
