package komu.adventofcode.aoc2020

private val fieldRegex = Regex("""(\w+):(\S+)""")
private val heightRegex = Regex("""(\d+)(\w+)""")
private val passportIdRegex = Regex("""\d{9}""")
private val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val validEyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun passportProcessing1(input: String): Int =
    input.split("\n\n").count { parsePassport(it).keys.containsAll(requiredFields) }

fun passportProcessing2(input: String): Int =
    input.split("\n\n").count { isValidPassport(parsePassport(it)) }

private fun parsePassport(passport: String) =
    fieldRegex.findAll(passport).map { it.groupValues[1] to it.groupValues[2] }.toMap()

private fun isValidPassport(passport: Map<String, String>): Boolean {
    val byr = passport["byr"]?.toIntOrNull() ?: return false
    val iyr = passport["iyr"]?.toIntOrNull() ?: return false
    val eyr = passport["eyr"]?.toIntOrNull() ?: return false
    val hgt = passport["hgt"] ?: return false
    val hcl = passport["hcl"] ?: return false
    val ecl = passport["ecl"] ?: return false
    val pid = passport["pid"] ?: return false

    return byr in 1920..2002
            && iyr in 2010..2020
            && eyr in 2020..2030
            && validateHeight(hgt)
            && hcl.matches(Regex("""#[0-9a-f]{6}"""))
            && ecl in validEyeColors
            && passportIdRegex.matches(pid)
}

private fun validateHeight(hgt: String): Boolean {
    val (_, heightStr, unit) = heightRegex.matchEntire(hgt)?.groupValues ?: return false
    val height = heightStr.toInt()

    return when (unit) {
        "cm" -> height in 150..193
        "in" -> height in 59..76
        else -> false
    }
}
