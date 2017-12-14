package komu.adventofcode

fun knotHash(input: String): String {
    val lengths = lengthsFromAscii(input)

    val sparseHash = knotHashArray(lengths, rounds = 64)
    return denseHash(sparseHash)
}

fun knotHashChecksum(lengths: List<Int>, size: Int = 256, rounds: Int = 1): Int {
    val array = knotHashArray(lengths, size, rounds)
    return array[0] * array[1]
}

private fun knotHashArray(lengths: List<Int>, size: Int = 256, rounds: Int): IntArray {
    val array = IntArray(size) { it }
    var position = 0
    var skip = 0

    repeat(rounds) {
        for (length in lengths) {
            reverse(array, position, length)
            position = (position + length + skip) % array.size
            skip++
        }
    }

    return array
}

private fun reverse(array: IntArray, position: Int, length: Int) {
    for (i in 0 until length / 2)
        array.swap((position + i) % array.size, (position + length - i - 1) % array.size)
}

private fun lengthsFromAscii(str: String): List<Int> =
        str.map { it.toInt() } + listOf(17, 31, 73, 47, 23)

private fun denseHash(sparseHash: IntArray): String =
        sparseHash.toList()
                .chunked(16)
                .map { it.reduce { a, b -> a xor b } }
                .octetsToHex()
