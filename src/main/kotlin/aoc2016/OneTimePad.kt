package komu.adventofcode.aoc2016

import komu.adventofcode.utils.hexEncodedMd5Hash

fun oneTimePad1(salt: String) =
    oneTimePad(salt, extraHashes = 0)

fun oneTimePad2(salt: String) =
    oneTimePad(salt, extraHashes = 2016)

private fun oneTimePad(salt: String, extraHashes: Int): Int {
    var matches = 0

    val futureKeys = ArrayDeque<OneTimePadKey>(1000)
    for (i in 0..999)
        futureKeys += OneTimePadKey(salt, i, extraHashes)

    var i = 0
    while (true) {
        val key = futureKeys.removeFirst()
        futureKeys.addLast(OneTimePadKey(salt, i + 1000, extraHashes))

        val valid = key.isValid(futureKeys)
        if (valid) {
            matches++
            println("$matches: $i")
        }

        if (matches == 64)
            return i

        i++
    }
}

private class OneTimePadKey(salt: String, n: Int, extraHashes: Int) {
    private val hash = computeHash(salt, n, extraHashes)
    private val seq = hash.windowed(3).find { it[0] == it[1] && it[0] == it[2] }?.get(0)?.let { c -> "$c$c$c$c$c" }

    fun isValid(futureKeys: Collection<OneTimePadKey>): Boolean =
        seq != null && futureKeys.any { it.seq != null && seq in it.hash }

    companion object {
        private fun computeHash(salt: String, n: Int, extraHashes: Int): String {
            var h = hexEncodedMd5Hash("$salt$n")
            repeat(extraHashes) {
                h = hexEncodedMd5Hash(h)
            }
            return h
        }
    }
}

