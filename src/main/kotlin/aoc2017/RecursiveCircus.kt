package komu.adventofcode.aoc2017

import komu.adventofcode.utils.nonEmptyLines

fun recursiveCircusBottom(input: String): String =
    buildTree(input).name

fun recursiveCircusBalance(input: String): Int =
    adjustBalance(buildTree(input), 0) // expected value does not matter at the root

private fun adjustBalance(node: Node, expected: Int): Int {
    val nodesByWeights = node.children.groupBy { it.totalWeight }.entries

    // Either we had no children or the children were already in balance, we need to adjust
    // the weight of this node directly.
    if (nodesByWeights.size < 2)
        return node.weight - (node.totalWeight - expected)

    // Otherwise we assume that exactly one node has illegal weight: all nodes are grouped into two slots
    check(nodesByWeights.size == 2)

    val unbalancedNode = nodesByWeights.find { it.value.size == 1 }!!.value.single()
    val expectedWeight = nodesByWeights.find { it.value.size > 1 }!!.key

    return adjustBalance(unbalancedNode, expectedWeight)
}

private fun buildTree(input: String): Node {
    val definitions = input.nonEmptyLines().map(Definition.Companion::parse)
    val nodesByName = definitions.map { Node(it.name, it.weight) }.associateBy { it.name }
    val rootCandidates = nodesByName.values.toMutableSet()

    for (definition in definitions) {
        val node = nodesByName[definition.name]!!

        for (childName in definition.children) {
            val child = nodesByName[childName]!!
            node.children += child
            rootCandidates -= child
        }
    }

    return rootCandidates.single()
}

private class Node(val name: String, val weight: Int) {
    val children = mutableListOf<Node>()

    val totalWeight: Int
        get() = weight + children.sumBy { it.totalWeight }
}

private data class Definition(val name: String, val weight: Int, val children: List<String>) {
    companion object {
        private val regex = Regex("""(\w+) \((\d+)\)( -> (.+))?""")

        fun parse(s: String): Definition {
            val m = regex.matchEntire(s) ?: error("no match for '$s'")

            return Definition(
                    name = m.groupValues[1],
                    weight = m.groupValues[2].toInt(),
                    children = m.groups[4]?.value?.split(", ") ?: emptyList())
        }
    }
}
