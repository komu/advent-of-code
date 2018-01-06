package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun totalWrappingPaperNeeded(s: String) =
        s.nonEmptyLines().map { Box.parse(it) }.sumBy { it.wrappingNeeded() }

fun totalRibbonNeeded(s: String) =
        s.nonEmptyLines().map { Box.parse(it) }.sumBy { it.ribbonNeeded() }

class Box(private val length: Int, private val width: Int, private val height: Int) {

    fun wrappingNeeded(): Int {
        val side1 = length * width
        val side2 = width * height
        val side3 = height * length
        val extra = side1.coerceAtMost(side2).coerceAtMost(side3)

        return 2 * side1 + 2 * side2 + 2 * side3 + extra
    }

    fun ribbonNeeded(): Int {
        val wrap = ((length + width + height) - (length.coerceAtLeast(width).coerceAtLeast(height))) * 2
        val bow = length * width * height

        return wrap + bow
    }

    companion object {
        fun parse(s: String): Box {
            val (l, w, h) = s.split("x").map { it.toInt() }
            return Box(l, w, h)
        }
    }
}
