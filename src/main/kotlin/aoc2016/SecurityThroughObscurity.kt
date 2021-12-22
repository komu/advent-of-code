package komu.adventofcode.aoc2016

fun securityThroughObscurity(input: List<String>): Int {
    val rooms = input.map { ObscureRoom(it) }

    return rooms.filter { it.isReal }.sumOf { it.sectorId }
}

fun securityThroughObscurity2(input: List<String>): Int {
    val rooms = input.map { ObscureRoom(it) }

    return rooms.find { it.isReal && it.decrypt() == "northpole object storage" }?.sectorId ?: -1
}

data class ObscureRoom(val name: String, val sectorId: Int, val checksum: String) {

    val isReal: Boolean
        get() {
            val letterCounts = name.filter { it.isLetter() }.groupingBy { it }.eachCount()
            val letters = letterCounts.entries.sortedBy { it.key }.sortedByDescending { it.value }.take(5)
                .joinToString("") { it.key.toString() }
            return checksum == letters
        }

    fun decrypt(): String =
        name.map { shift(it, sectorId) }.joinToString("")

    companion object {
        private const val alphabet = "abcdefghijklmnopqrstuvwxyz"

        private val pattern = Regex("""([a-z-]+)-(\d+)\[(.+)]""")

        operator fun invoke(s: String): ObscureRoom {
            val (name, sectorId, checksum) = pattern.matchEntire(s)?.destructured ?: error("invalid room '$s'")

            return ObscureRoom(name, sectorId.toInt(), checksum)
        }

        private fun shift(char: Char, count: Int): Char =
            if (char == '-')
                ' '
            else
                alphabet[(alphabet.indexOf(char) + count) % alphabet.length]
    }
}
