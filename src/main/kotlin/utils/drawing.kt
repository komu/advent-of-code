package utils

import komu.adventofcode.utils.Point

fun drawMap(points: Collection<Point>): String {
    if (points.isEmpty()) return "<empty>"
    val minX = points.map { it.x }.min()!!
    val maxX = points.map { it.x }.max()!!
    val minY = points.map { it.y }.min()!!
    val maxY = points.map { it.y }.max()!!

    return buildString {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val ch = if (Point(x, y) in points) '#' else '.'
                append(ch)
            }
            append('\n')
        }
    }
}
