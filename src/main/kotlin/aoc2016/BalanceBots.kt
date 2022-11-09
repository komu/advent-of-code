package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines

fun balanceBots1(input: String): Int =
    buildBalanceRules(input).first.find { it.low() == 17 && it.high() == 61 }?.id!!

fun balanceBots2(input: String): Int {
    val bins = buildBalanceRules(input).second

    return bins[0].value * bins[1].value * bins[2].value
}

private fun buildBalanceRules(input: String): Pair<List<Bot>, List<Bin>> {
    val bots = mutableMapOf<BotId, Bot>()
    val bins = mutableMapOf<BinId, Bin>()

    fun bot(id: BotId) = bots.getOrPut(id) { Bot(id) }
    fun bin(id: BotId) = bins.getOrPut(id) { Bin(id) }

    fun target(target: BalanceTarget) = when (target) {
        is BalanceTarget.BotTarget -> bot(target.id)
        is BalanceTarget.BinTarget -> bin(target.id)
    }

    for (line in input.nonEmptyLines()) {
        when (val rule = BalanceRule.parse(line)) {
            is BalanceRule.Give -> {
                val giver = bot(rule.giverId)

                target(rule.low).addSource { giver.low() }
                target(rule.high).addSource { giver.high() }
            }

            is BalanceRule.Value -> {
                val bot = bot(rule.botId)
                bot.addSource { rule.value }
            }
        }
    }

    return Pair(bots.values.sortedBy { it.id }, bins.values.sortedBy { it.id })
}

private sealed class BalanceRule {

    data class Value(val value: Int, val botId: BotId) : BalanceRule()
    data class Give(val giverId: BotId, val low: BalanceTarget, val high: BalanceTarget) : BalanceRule()

    companion object {

        private val giveRegex = Regex("""bot (\d+) gives low to (.+) and high to (.+)""")
        private val valueRegex = Regex("""value (\d+) goes to bot (\d+)""")

        fun parse(s: String): BalanceRule {
            giveRegex.matchEntire(s)?.destructured?.let { (giver, low, high) ->
                return Give(giver.toInt(), BalanceTarget.parse(low), BalanceTarget.parse(high))
            }
            valueRegex.matchEntire(s)?.destructured?.let { (value, bot) ->
                return Value(value.toInt(), bot.toInt())
            }
            error("invalid command '$s'")
        }
    }
}
private typealias Chip = Int

private interface ChipTarget {
    fun addSource(source: ChipSource)
}

private typealias ChipSource = () -> Chip

private class Bot(val id: BotId) : ChipTarget {

    private val sources = mutableListOf<ChipSource>()

    private val values: Pair<Int, Int> by lazy {
        check(sources.size == 2)
        val values = sources.map { it() }
        Pair(values.min(), values.max())
    }

    override fun addSource(source: ChipSource) {
        sources += source
    }

    fun low() = values.first
    fun high() = values.second
}

private class Bin(val id: BinId) : ChipTarget {

    private var source: ChipSource? = null

    val value: Int by lazy {
        this.source!!()
    }

    override fun addSource(source: ChipSource) {
        this.source = source
    }
}

private typealias BotId = Int
private typealias BinId = Int

private sealed class BalanceTarget {
    data class BotTarget(val id: BotId) : BalanceTarget()
    data class BinTarget(val id: BinId) : BalanceTarget()

    companion object {

        fun parse(s: String): BalanceTarget = when {
            s.startsWith("bot ") -> BotTarget(s.removePrefix("bot ").toInt())
            s.startsWith("output ") -> BinTarget(s.removePrefix("output ").toInt())
            else -> error("invalid target '$s'")
        }
    }
}

