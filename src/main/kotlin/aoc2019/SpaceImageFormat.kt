package aoc2019

import komu.adventofcode.utils.countOccurrences

fun spaceImageFormat1(w: Int, h: Int, input: String): Int {
    val minLayer = SpaceImage.parse(w, h, input).layers.minByOrNull { it.countOccurrences('0') }!!
    return minLayer.countOccurrences('1') * minLayer.countOccurrences('2')
}

fun spaceImageFormat2(w: Int, h: Int, input: String) =
    SpaceImage.parse(w, h, input).render()

class SpaceImage(private val width: Int, private val height: Int, val layers: List<String>) {

    fun render(): String {
        val result = CharArray(width * height) { i ->
            val px = layers.first { it[i] != '2' }[i]
            if (px == '0') '#' else ' '
        }

        return String(result).chunked(width).joinToString("\n")
    }

    companion object {
        fun parse(w: Int, h: Int, data: String) =
            SpaceImage(w, h, data.trim().chunked(w * h))
    }
}
