package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.product

fun tobogganTrajectory(dx: Int, dy: Int, map: String): Int {
    val rows = map.nonEmptyLines()

    var x = dx
    var y = dy
    var trees = 0
    while (y < rows.size) {
        val row = rows[y]
        if (row[x % row.length] == '#')
            trees += 1

        x += dx
        y += dy
    }

    return trees
}

fun tobogganTrajectory2(map: String): Long {
    val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
    return slopes.map { (dx, dy) -> tobogganTrajectory(dx, dy, map).toLong() }.product()
}
