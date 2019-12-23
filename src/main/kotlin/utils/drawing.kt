package utils

import komu.adventofcode.utils.Point

fun drawMap(points: Collection<Point>, markings: Map<Point, Char> = emptyMap()): String {
    if (points.isEmpty()) return "<empty>"
    val pts = points + markings.keys
    val minX = pts.map { it.x }.min()!!
    val maxX = pts.map { it.x }.max()!!
    val minY = pts.map { it.y }.min()!!
    val maxY = pts.map { it.y }.max()!!

    return buildString {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val p = Point(x, y)
                append(markings[p] ?: if (p in points) '#' else '.')
            }
            append('\n')
        }
    }
}
