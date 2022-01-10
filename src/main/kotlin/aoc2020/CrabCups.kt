package komu.adventofcode.aoc2020

fun crabCups1(labels: String) =
    crapCups(labels, iterations = 100, maxLabel = 9).answer()

fun crabCups2(labels: String): Long {
    val one = crapCups(labels, iterations = 10_000_000, maxLabel = 1_000_000)
    return one.next.label.toLong() * one.next.next.label
}

private fun crapCups(labels: String, iterations: Int, maxLabel: Int): CupCircleNode {
    val first = CupCircleNode.build(labels, maxLabel)

    val nodesByLabels = Array(maxLabel + 1) { first }
    for (node in first)
        nodesByLabels[node.label] = node

    var current = first

    repeat(iterations) {
        val firstRemoved = current.next
        val lastRemoved = firstRemoved.next.next

        val afterLast = lastRemoved.next
        current.next = afterLast

        val destinationLabel = pickDestinationLabel(current.label - 1, firstRemoved, maxLabel)
        val destination = nodesByLabels[destinationLabel]

        val next = destination.next

        destination.next = firstRemoved

        lastRemoved.next = next
        current = current.next
    }

    return nodesByLabels[1]
}

private fun pickDestinationLabel(firstCandidate: Int, firstRemoved: CupCircleNode, maxLabel: Int): Int {
    val illegal1 = firstRemoved.label
    val illegal2 = firstRemoved.next.label
    val illegal3 = firstRemoved.next.next.label

    var value = if (firstCandidate == 0) maxLabel else firstCandidate

    while (value == illegal1 || value == illegal2 || value == illegal3)
        value = if (value == 1) maxLabel else (value - 1)

    return value
}

private class CupCircleNode(val label: Int) : Iterable<CupCircleNode> {
    lateinit var next: CupCircleNode

    override fun iterator() = iterator {
        val first = this@CupCircleNode
        var node = first
        do {
            yield(node)
            node = node.next
        } while (node != first)
    }

    fun answer() =
        drop(1).map { it.label }.joinToString("")

    companion object {
        fun build(labelString: String, maxLabel: Int): CupCircleNode {
            val givenLabels = labelString.map { it.toString().toInt() }

            val first = CupCircleNode(givenLabels.first())
            var previous = first

            for (cup in givenLabels.drop(1)) {
                val node = CupCircleNode(cup)
                previous.next = node
                previous = node
            }

            for (cup in 10..maxLabel) {
                val node = CupCircleNode(cup)
                previous.next = node
                previous = node
            }

            previous.next = first

            return first
        }
    }
}
