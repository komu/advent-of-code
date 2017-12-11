package komu.adventofcode

fun inverseCaptcha(s: String): Int =
    inverseCaptcha(s.map { it.toString().toInt() })

fun inverseCaptcha(input: List<Int>, offset: Int = 1): Int =
    input.filterIndexed { i, digit ->
        digit == input[(i + offset) % input.size]
    }.sum()

fun inverseCaptcha2(s: String): Int =
    inverseCaptcha2(s.map { it.toString().toInt() })

fun inverseCaptcha2(input: List<Int>): Int =
    inverseCaptcha(input, input.size/2)
