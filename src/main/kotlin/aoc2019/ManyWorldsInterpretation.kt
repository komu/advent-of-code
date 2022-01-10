package komu.adventofcode.aoc2019

import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines
import utils.shortestPathBetween
import utils.shortestPathWithCost

private typealias LockType = Int
private typealias CollectedKeys = Int

fun manyWorldsInterpretation(input: String): Int =
    Vault.parse(input).solve()

private class Vault private constructor(
    tiles: Collection<Tile>,
    private val starts: List<Tile>
) {

    private val keyCount = tiles.count { it.key != null }
    private val keysByType = tiles.filter { it.key != null }.associateBy { it.key!! }
    private val pathCache = mutableMapOf<PathCacheKey, List<Tile>>()

    fun solve(): Int {
        val (_, cost) = shortestPathWithCost(State(starts), { it.keyCount == keyCount }) { state ->
            val result = mutableListOf<Pair<State, Int>>()
            for ((i, from) in state.tiles.withIndex()) {
                for ((key, keyTile) in keysByType.entries) {
                    if (!state.hasKey(key)) {
                        val path = path(from, keyTile, state.collectedKeys)
                        if (path != null && path.hasNoUnCollectedKeys(state.collectedKeys))
                            result += state.collectKeyAt(i, keyTile, key) to path.size
                    }
                }
            }
            result
        } ?: error("no path")

        return cost
    }

    private fun List<Tile>.hasNoUnCollectedKeys(collectedKeys: CollectedKeys): Boolean =
        subList(0, size - 1).none { it.key != null && it.key !in collectedKeys }

    private fun path(from: Tile, to: Tile, collectedKeys: CollectedKeys): List<Tile>? =
        pathCache.getOrPut(PathCacheKey(collectedKeys, from, to)) {
            shortestPathBetween(from, to) { it.accessibleNeighbors(collectedKeys) }.orEmpty()
        }.takeUnless { it.isEmpty() }

    private data class PathCacheKey(val from: Tile, val to: Tile, val collectedKeys: CollectedKeys) {
        constructor(collectedKeys: CollectedKeys, from: Tile, to: Tile) : this(
            if (from.hashCode() < to.hashCode()) from else to,
            if (from.hashCode() < to.hashCode()) to else from,
            collectedKeys
        )
    }

    private data class State(val tiles: List<Tile>, val collectedKeys: CollectedKeys = 0) {
        val keyCount = Integer.bitCount(collectedKeys)

        fun hasKey(key: LockType) =
            key in collectedKeys

        fun collectKeyAt(index: Int, to: Tile, key: LockType): State {
            val tiles = tiles.toMutableList()
            tiles[index] = to
            return State(tiles, collectedKeys or key)
        }
    }

    private class Tile(val lock: LockType? = null, val key: LockType? = null) {

        private var neighbors: List<Tile> = emptyList()
        private var unlockedNeighbors = true

        fun initNeighbors(neighbors: List<Tile>) {
            this.neighbors = neighbors
            this.unlockedNeighbors = neighbors.all { it.lock == null }
        }

        fun accessibleNeighbors(keys: CollectedKeys): List<Tile> =
            if (unlockedNeighbors) neighbors else neighbors.filter { it.canPass(keys) }

        fun canPass(keys: CollectedKeys) =
            lock == null || lock in keys
    }

    companion object {
        private operator fun CollectedKeys.contains(key: LockType) =
            (this and key) != 0

        private fun encodeKey(key: Char): Int =
            1 shl (key.toUpperCase() - 'A')

        fun parse(input: String): Vault {
            val starts = mutableListOf<Tile>()
            val tilesByPoint = mutableMapOf<Point, Tile>()
            for ((y, line) in input.nonEmptyLines().withIndex()) {
                for ((x, c) in line.withIndex()) {
                    val point = Point(x, y)
                    when {
                        c == '@' -> {
                            val tile = Tile()
                            starts += tile
                            tilesByPoint[point] = tile
                        }
                        c == '.' ->
                            tilesByPoint[point] = Tile()
                        c.isLowerCase() ->
                            tilesByPoint[point] = Tile(key = encodeKey(c))
                        c.isUpperCase() ->
                            tilesByPoint[point] = Tile(lock = encodeKey(c))
                    }
                }
            }

            for ((point, tile) in tilesByPoint)
                tile.initNeighbors(point.neighbors.mapNotNull { tilesByPoint[it] })

            return Vault(tilesByPoint.values.toList(), starts)
        }
    }
}
