package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.permutations

fun scrambledLettersAndHash1(input: String): String {
    val steps = input.nonEmptyLines().map { ScrambleStep.parse(it) }

    var text = "abcdefgh"
    for (step in steps)
        text = step.execute(text)

    return text
}

fun scrambledLettersAndHash2(input: String): String {
    val steps = input.nonEmptyLines().map { ScrambleStep.parse(it) }

    var text = "fbgdceah"
    for (step in steps.asReversed())
        text = step.undo(text)

    return text
}

fun String.swap(j: Int, k: Int): String =
    String(CharArray(length) { i ->
        this[when (i) {
            j -> k
            k -> j
            else -> i
        }]
    })

fun String.rotateLeft(steps: Int) =
    substring(steps % length) + substring(0, steps % length)

fun String.rotateRight(steps: Int) =
    substring(length - steps % length) + substring(0, length - steps % length)

fun String.reverse(from: Int, to: Int): String = String(CharArray(length) { i ->
    this[if (i in from..to) (from + (to - i)) else i]
})

fun String.rotateBasedOnLetter(letter: Char): String {
    val index = indexOf(letter)
    return if (index < 4)
        rotateRight(index + 1)
    else
        rotateRight(index + 2)
}

fun String.move(from: Int, to: Int): String {
    val result = toMutableList()
    result.add(to, result.removeAt(from))
    return result.joinToString("")
}

private fun bruteReverse(s: String, op: (String) -> String): String {
    for (permutation in s.toList().permutations()) {
        val input = permutation.joinToString("")
        if (op(input) == s)
            return input
    }
    error("could not find reverse for $s")
}

private sealed class ScrambleStep {

    data class Swap(val x: Int, val y: Int) : ScrambleStep()
    data class SwapLetter(val x: Char, val y: Char) : ScrambleStep()
    data class RotateLeft(val steps: Int) : ScrambleStep()
    data class RotateRight(val steps: Int) : ScrambleStep()
    data class Move(val from: Int, val to: Int) : ScrambleStep()
    data class Reverse(val start: Int, val end: Int) : ScrambleStep()
    data class RotateBasedOnPositionOfLetter(val letter: Char) : ScrambleStep()

    fun execute(s: String): String = when (this) {
        is Swap -> s.swap(x, y)
        is SwapLetter -> s.swap(s.indexOf(x), s.indexOf(y))
        is RotateLeft -> s.rotateLeft(steps)
        is RotateRight -> s.rotateRight(steps)
        is RotateBasedOnPositionOfLetter -> s.rotateBasedOnLetter(letter)
        is Move -> s.move(from, to)
        is Reverse -> s.reverse(start, end)
    }

    fun undo(s: String): String = when (this) {
        is Swap -> s.swap(x, y)
        is RotateLeft -> s.rotateRight(steps)
        is RotateRight -> s.rotateLeft(steps)
        is Reverse -> s.reverse(start, end)
        is SwapLetter, is RotateBasedOnPositionOfLetter, is Move -> bruteReverse(s, this::execute)
    }

    companion object {

        private val swapRegex = Regex("""swap position (\d+) with position (\d+)""")
        private val moveRegex = Regex("""move position (\d+) to position (\d+)""")
        private val rotateLeftRegex = Regex("""rotate left (\d+) step(s)?""")
        private val rotateRightRegex = Regex("""rotate right (\d+) step(s)?""")
        private val reverseRegex = Regex("""reverse positions (\d+) through (\d+)""")
        private val rotateBasedOnPositionOfLetterRegex = Regex("""rotate based on position of letter (.)""")
        private val swapLetterRegex = Regex("""swap letter (.) with letter (.)""")

        fun parse(s: String): ScrambleStep {
            swapRegex.matchEntire(s)?.destructured?.let { (x, y) -> return Swap(x.toInt(), y.toInt()) }
            swapLetterRegex.matchEntire(s)?.destructured?.let { (x, y) -> return SwapLetter(x[0], y[0]) }
            rotateLeftRegex.matchEntire(s)?.destructured?.let { (steps) -> return RotateLeft(steps.toInt()) }
            rotateRightRegex.matchEntire(s)?.destructured?.let { (steps) -> return RotateRight(steps.toInt()) }
            moveRegex.matchEntire(s)?.destructured?.let { (from, to) -> return Move(from.toInt(), to.toInt()) }
            reverseRegex.matchEntire(s)?.destructured?.let { (from, to) -> return Reverse(from.toInt(), to.toInt()) }
            rotateBasedOnPositionOfLetterRegex.matchEntire(s)?.destructured?.let { (letter) ->
                return RotateBasedOnPositionOfLetter(letter[0])
            }

            error("invalid step '$s'")
        }
    }
}