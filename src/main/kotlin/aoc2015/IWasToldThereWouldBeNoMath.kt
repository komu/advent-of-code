package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun totalWrappingPaperNeeded(s: String) = s.nonEmptyLines().sumOf {
    val (l, w, h) = parseBox(it)

    val s1 = l * w
    val s2 = w * h
    val s3 = h * l

    2 * s1 + 2 * s2 + 2 * s3 + minOf(s1, s2, s3)
}

fun totalRibbonNeeded(s: String) = s.nonEmptyLines().sumOf {
    val (l, w, h) = parseBox(it)

    val wrap = ((l + w + h) - (l.coerceAtLeast(w).coerceAtLeast(h))) * 2
    val bow = l * w * h

    wrap + bow
}

private fun parseBox(s: String) =
    s.split("x").map { it.toInt() }
