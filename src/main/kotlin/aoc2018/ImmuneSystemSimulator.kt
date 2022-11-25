package komu.adventofcode.aoc2018

import komu.adventofcode.utils.nonEmptyLines
import java.util.*

fun immuneSystemSimulator1(input: String): Int {
    val (x, y) = runSimulation(input, boost = 0)
    return maxOf(x, y)
}

fun immuneSystemSimulator2(input: String): Int {
    var min = 0
    var max = 1_000_000

    while (min < max) {
        val mid = (min + max) / 2
        if (immuneSystemWinsWithBoost(input, mid)) {
            max = mid
        } else {
            min = mid + 1
        }
    }
    return runSimulation(input, min).first
}

private fun immuneSystemWinsWithBoost(input: String, boost: Int): Boolean {
    val (immune, infection) = runSimulation(input, boost = boost)
    return immune > 0 && infection == 0
}

private fun runSimulation(input: String, boost: Int): Pair<Int, Int> {
    val lines = input.nonEmptyLines()
    val immuneSystem = Army.parse(lines.takeWhile { it != "Infection:" }.drop(1), boost = boost)
    val infection = Army.parse(lines.dropWhile { it != "Infection:" }.drop(1))

    while (immuneSystem.groups.isNotEmpty() && infection.groups.isNotEmpty()) {
        val remaining = immuneSystem.remainingUnits + infection.remainingUnits
        fight(immuneSystem, infection)

        if (remaining == immuneSystem.remainingUnits + infection.remainingUnits)
            break
    }

    return Pair(immuneSystem.remainingUnits, infection.remainingUnits)
}

private fun fight(immuneSystem: Army, infection: Army) {
    val targets = immuneSystem.selectTargetsFrom(infection) + infection.selectTargetsFrom(immuneSystem)

    val groups = (immuneSystem.groups + infection.groups).sortedWith(Group.attackOrder)

    for (group in groups) {
        val target = targets[group] ?: continue
        target.takeDamage(group.damageTo(target))
    }

    immuneSystem.removeEmptyGroups()
    infection.removeEmptyGroups()
}

private class Army(
    var groups: List<Group>
) {

    val remainingUnits: Int
        get() = groups.sumOf { it.units }

    fun selectTargetsFrom(defendingArmy: Army): Map<Group, Group> {
        val remainingDefenders = defendingArmy.groups.toMutableList()
        val targetMapping = IdentityHashMap<Group, Group>()

        for (attacker in groups.sortedWith(Group.selectionOrder)) {
            val defender = remainingDefenders.maxWithOrNull(Group.targetOrdering(attacker))
            if (defender != null && attacker.damageTo(defender) > 0) {
                remainingDefenders.remove(defender)
                targetMapping[attacker] = defender
            }
        }
        return targetMapping
    }

    fun removeEmptyGroups() {
        groups = groups.filter { it.units > 0 }
    }

    companion object {
        fun parse(lines: List<String>, boost: Int = 0) =
            Army(lines.map { Group.parse(it, boost) })
    }
}

private class Group(
    var units: Int,
    private val hp: Int,
    val weaknesses: List<String>,
    val immunities: List<String>,
    private val attackDamage: Int,
    val attackType: String,
    val initiative: Int,
) {

    fun damageTo(target: Group): Int = when (attackType) {
        in target.immunities -> 0
        in target.weaknesses -> 2 * effectivePower
        else -> effectivePower
    }

    fun takeDamage(damage: Int) {
//        var remainingDamage = damage
//        while (remainingDamage >= hp) {
//            units--
//            remainingDamage -= hp
//        }

//        units = units.coerceAtLeast(0)
        units -= (damage / hp).coerceAtMost(units)
    }

    val effectivePower: Int
        get() = units * attackDamage

    companion object {
        private val regex =
            Regex("""(\d+) units each with (\d+) hit points( \((.+)\))? with an attack that does (\d+) (.+) damage at initiative (\d+)""")

        fun parse(s: String, boost: Int): Group {
            val (count, hp, _, modifiers, attackDamage, attackDamageType, initiative) = regex.matchEntire(s)?.destructured
                ?: error("invalid '$s'")

            val (weaknesses, immunities) = parseModifiers(modifiers)

            return Group(
                count.toInt(),
                hp.toInt(),
                weaknesses,
                immunities,
                attackDamage.toInt() + boost,
                attackDamageType,
                initiative.toInt()
            )
        }

        private fun parseModifiers(s: String): Pair<List<String>, List<String>> {
            var weaknesses = emptyList<String>()
            var immunities = emptyList<String>()

            for (part in s.split("; ")) {
                if (part.startsWith("immune to "))
                    immunities = part.removePrefix("immune to ").split(", ")
                if (part.startsWith("weak to "))
                    weaknesses = part.removePrefix("weak to ").split(", ")
            }

            return Pair(weaknesses, immunities)
        }

        val selectionOrder: Comparator<Group> =
            compareByDescending<Group> { it.effectivePower }.thenByDescending { it.initiative }

        val attackOrder: Comparator<Group> =
            compareByDescending { it.initiative }

        fun targetOrdering(attacker: Group): Comparator<Group> =
            compareBy<Group> { attacker.damageTo(it) }
                .thenBy { it.effectivePower }
                .thenBy { it.initiative }
    }
}



