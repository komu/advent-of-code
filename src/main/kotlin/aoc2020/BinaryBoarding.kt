package komu.adventofcode.aoc2020

fun parseBoardingPassCode(code: String) =
    code.fold(0) { n, c -> n * 2 + if (c in "BR") 1 else 0 }

fun binaryBoarding1(lines: List<String>) =
    lines.maxOf { parseBoardingPassCode(it) }

fun binaryBoarding2(lines: List<String>): Int {
    val taken = lines.map { parseBoardingPassCode(it) }.toSet()
    return (0..127 * 8).first { it !in taken && (it - 1) in taken && (it + 1) in taken }
}
