package komu.adventofcode

fun pipesConnectedTo(startPipe: Int, input: String): Int {
    val pipesById = buildPipes(input)

    val start = pipesById[startPipe]!!
    val connected = mutableSetOf<Int>()
    collectConnected(start, connected)

    return connected.size
}

fun totalPipeGroups(input: String): Int {
    val pipes = buildPipes(input).values.toMutableSet()

    var count = 0
    while (pipes.isNotEmpty()) {
        removeReachable(pipes.first(), pipes)
        count++
    }

    return count
}

private fun removeReachable(pipe: Pipe, reachable: MutableSet<Pipe>) {
    if (reachable.remove(pipe))
        for (neighbor in pipe.connections)
            removeReachable(neighbor, reachable)
}

private fun collectConnected(pipe: Pipe, visited: MutableSet<Int>) {
    if (visited.add(pipe.id))
        for (neighbor in pipe.connections)
            collectConnected(neighbor, visited)
}

private fun buildPipes(input: String): Map<Int, Pipe> {
    val definitions = input.nonEmptyLines().map(PipeDefinition.Companion::parse)

    val pipesById = definitions.map { Pipe(it.id) }.associateBy { it.id }

    for (definition in definitions) {
        val pipe = pipesById[definition.id]!!
        for (connection in definition.connections) {
            val neighbor = pipesById[connection]!!

            pipe.connections += neighbor
            neighbor.connections += pipe
        }
    }
    return pipesById
}

private class Pipe(val id: Int) {
    val connections = mutableListOf<Pipe>()
}

private data class PipeDefinition(val id: Int, val connections: List<Int>) {
    companion object {

        private val regex = Regex("""(\d+) <-> (.+)""")
        fun parse(line: String): PipeDefinition {
            val m = regex.matchEntire(line) ?: error("no match for '$line'")

            return PipeDefinition(
                    id = m.groupValues[1].toInt(),
                    connections = m.groupValues[2].commaSeparatedInts())
        }
    }
}
