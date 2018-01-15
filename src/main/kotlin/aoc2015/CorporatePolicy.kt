package komu.adventofcode.aoc2015

fun corporatePolicy(password: String): String {
    val pass = password.toCharArray()
    do {
        pass.increment()
    } while (!pass.isValidPassword())

    return String(pass)
}

private fun CharArray.increment() {
    for (i in indices.reversed()) {
        if (this[i] < 'z') {
            this[i]++
            break
        } else {
            this[i] = 'a'
        }
    }
}

private fun CharArray.isValidPassword() = hasIncreasingStraight() && hasNoInvalidLetters() && hasPairs()

private fun CharArray.hasIncreasingStraight() = asList().windowed(3).any { it[0]+1 == it[1] && it[1]+1 == it[2] }
private fun CharArray.hasNoInvalidLetters() = 'i' !in this && 'o' !in this && 'l' !in this

private fun CharArray.hasPairs(): Boolean {
    val windowIndices = asList().windowed(2).withIndex().filter { it.value[0] == it.value[1] }.map { it.index }

    val max = windowIndices.max() ?: return false
    val min = windowIndices.min() ?: return false

    return max - min >= 2
}
