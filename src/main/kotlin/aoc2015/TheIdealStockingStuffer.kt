package komu.adventofcode.aoc2015

import komu.adventofcode.utils.hexEncode
import java.security.MessageDigest

fun idealStockingStuffer(input: String, zeroes: Int): Int {
    val prefix = "0".repeat(zeroes)
    return (1..Int.MAX_VALUE).find { hexEncodedMd5Hash(input + it).startsWith(prefix) } ?: error("could not find hash")
}

private fun hexEncodedMd5Hash(s: String) =
        MessageDigest.getInstance("MD5").digest(s.toByteArray()).hexEncode()
