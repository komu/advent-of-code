package aoc2019

import komu.adventofcode.utils.nonEmptyLines

fun spaceStoichiometry1(input: String): Long {
    val rules = input.nonEmptyLines().map { Rule.parse(it) }

    return requirements(rules, 1)
}

private fun requirements(rules: List<Rule>, fuel: Long): Long {
    val stock = Stock()
    stock.take("FUEL", fuel)

    while (true) {
        val material = stock.findMaterialNeedingReplenishing() ?: break

        val count = stock[material]
        check(count < 0)
        val rule = rules.findRule(material)
        val batches = rule.batchesNeeded(-count)

        for (req in rule.requirements)
            stock.take(req.material, req.units * batches)

        stock.add(rule.target, rule.produces * batches)
    }

    return stock.ore
}

fun spaceStoichiometry2(input: String): Long {
    val rules = input.nonEmptyLines().map { Rule.parse(it) }

    val trillion = 1000000000000L

    var low = 0L
    var high = 1000000000L

    var mid = 0L
    while (low <= high) {
        mid = (low + high) / 2

        val result = requirements(rules, mid)
        when {
            result < trillion -> low = mid + 1
            result > trillion -> high = mid - 1
            else -> return mid
        }
    }

    return if (requirements(rules, mid) > trillion) mid - 1 else mid
}

private class Stock {

    val stock = mutableMapOf<String, Long>()
    var ore = 0L

    operator fun get(material: String): Long =
        stock[material] ?: 0

    fun findMaterialNeedingReplenishing(): String? =
        stock.entries.find { it.value < 0 }?.key

    fun add(material: String, units: Long) {
        stock[material] = this[material] + units
    }

    fun take(material: String, units: Long) {
        if (material == "ORE") {
            ore += units
        } else {
            stock[material] = this[material] - units
        }
    }
}

private fun List<Rule>.findRule(target: String): Rule =
    find { it.target == target } ?: error("no rule for $target")

private class MaterialCount(val units: Long, val material: String) {

    override fun toString() = "$units $material"

    companion object {
        private val regex = Regex("""(\d+) (\w+)""")
        fun parse(input: String): MaterialCount {
            val (_, count, material) = regex.matchEntire(input)?.groupValues ?: error("invalid input '$input'")
            return MaterialCount(count.toLong(), material)
        }
    }
}

private class Rule(val requirements: List<MaterialCount>, val target: String, val produces: Long) {

    override fun toString() = "${requirements.joinToString()} => $produces $target"

    fun batchesNeeded(count: Long) = 1 + ((count - 1) / produces)

    companion object {
        private val regex = Regex("""(.+) => (\d+) (.+)""")
        fun parse(input: String): Rule {
            val (_, requirements, produces, material) = regex.matchEntire(input)?.groupValues
                ?: error("invalid input '$input'")
            return Rule(
                requirements = requirements.split(Regex(", ")).map { MaterialCount.parse(it) },
                target = material,
                produces = produces.toLong()
            )
        }
    }
}
