package utils

import java.util.*

fun <T> shortestPath(from: T, isTarget: (T) -> Boolean, edges: (T) -> List<T>): List<T>? =
    shortestPathWithCost(from, isTarget) { n -> edges(n).map { it to 1 } }?.first

fun <T> shortestPathBetween(from: T, to : T, edges: (T) -> List<T>): List<T>? =
    shortestPath(from, { it == to }, edges)

fun <T> shortestPathWithCost(from: T, isTarget: (T) -> Boolean, edges: (T) -> List<Pair<T, Int>>): Pair<List<T>, Int>? {
    val initial = PathNode(from, null, 0)
    val nodes = mutableMapOf(from to initial)
    val queue = PriorityQueue(setOf(initial))
    val targets = mutableSetOf<T>()

    while (queue.isNotEmpty()) {
        val u = queue.remove()

        for ((v, cost) in edges(u.point)) {
            if (isTarget(v))
                targets += v

            val newDistance = u.distance + cost
            val previousNode = nodes[v]
            if (previousNode == null || newDistance < previousNode.distance) {
                val newNode = PathNode(v, u, newDistance)
                nodes[v] = newNode
                queue += newNode
            }
        }
    }

    val targetNode = targets.map { nodes[it]!! }.minByOrNull { it.distance }
    if (targetNode != null) {
        val result = mutableListOf<T>()
        var node: PathNode<T>? = targetNode
        while (node?.previous != null) {
            result += node.point
            node = node.previous
        }
        return result.asReversed() to targetNode.distance
    }
    return null
}

private class PathNode<T>(val point: T, val previous: PathNode<T>?, val distance: Int) : Comparable<PathNode<T>> {
    override fun compareTo(other: PathNode<T>) = distance.compareTo(other.distance)
}
