package komu.adventofcode

fun seriesOfTubes(input: String): Pair<String, Int> {
    val diagram = Diagram(input)
    var pos = diagram.start
    var dir = Direction.DOWN

    val output = StringBuilder()
    var steps = 0

    while (pos in diagram) {
        val ch = diagram[pos]
        if (ch.isLetter()) {
            output.append(ch)
        } else if (ch == '+') {
            for (d in Direction.values()) {
                val neighbor = pos + d
                if (neighbor in diagram && !d.isOpposite(dir) && diagram[neighbor] != ' ') {
                    dir = d
                    break
                }
            }
        } else if (ch != '|' && ch != '-') {
            break
        }

        steps++
        pos += dir
    }

    return Pair(output.toString(), steps)
}

private class Diagram(input: String) {
    private val grid = input.lines()

    val start = Point(grid.first().indexOf('|'), 0)

    operator fun contains(p: Point) = p.y in grid.indices && p.x in grid[p.y].indices

    operator fun get(p: Point) = grid[p.y][p.x]
}


