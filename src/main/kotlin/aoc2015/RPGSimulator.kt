package komu.adventofcode.aoc2015

fun rpgSimulator1(boss: RPGCharacter) =
    Item.validCombinations().filter { isWinningGear(it, boss) }.minOf { it.totalCost() }

fun rpgSimulator2(boss: RPGCharacter) =
    Item.validCombinations().filter { !isWinningGear(it, boss) }.maxOf { it.totalCost() }

private fun isWinningGear(gear: List<Item>, boss: RPGCharacter): Boolean {
    val player = RPGCharacter(hitPoints = 100, damage = gear.sumOf { it.damage }, armor = gear.sumOf { it.armor })

    var playerHp = player.hitPoints
    var bossHp = boss.hitPoints

    while (true) {
        bossHp -= player.hitDamageTo(boss)
        if (bossHp <= 0)
            return true

        playerHp -= boss.hitDamageTo(player)
        if (playerHp <= 0)
            return false
    }
}

private fun List<Item>.totalCost() = sumOf { it.cost }

class RPGCharacter(val hitPoints: Int, val damage: Int, val armor: Int) {

    fun hitDamageTo(target: RPGCharacter): Int =
        (damage - target.armor).coerceAtLeast(1)
}

private class Item(@Suppress("unused") val name: String, val cost: Int, val damage: Int, val armor: Int) {

    companion object {

        fun validCombinations() = sequence {
            for (weapon in weapons)
                for (armor in armors)
                    for (ring1 in rings)
                        for (ring2 in rings)
                            if (ring1 != ring2)
                                yield(listOf(weapon, armor, ring1, ring2))
        }

        val weapons = listOf(
            Item(name = "Dagger", cost = 8, damage = 4, armor = 0),
            Item(name = "Shortsword", cost = 10, damage = 5, armor = 0),
            Item(name = "Warhammer", cost = 25, damage = 6, armor = 0),
            Item(name = "Longsword", cost = 40, damage = 7, armor = 0),
            Item(name = "Greataxe", cost = 74, damage = 8, armor = 0)
        )

        val armors = listOf(
            Item(name = "-", cost = 0, damage = 0, armor = 0),
            Item(name = "Leather", cost = 13, damage = 0, armor = 1),
            Item(name = "Chainmail", cost = 31, damage = 0, armor = 2),
            Item(name = "Splintmail", cost = 53, damage = 0, armor = 3),
            Item(name = "Bandedmail", cost = 75, damage = 0, armor = 4),
            Item(name = "Platemail", cost = 102, damage = 0, armor = 5)
        )

        val rings = listOf(
            Item(name = "-", cost = 0, damage = 0, armor = 0),
            Item(name = "-", cost = 0, damage = 0, armor = 0),
            Item(name = "Damage +1", cost = 25, damage = 1, armor = 0),
            Item(name = "Damage +2", cost = 50, damage = 2, armor = 0),
            Item(name = "Damage +3", cost = 100, damage = 3, armor = 0),
            Item(name = "Defense +1", cost = 20, damage = 0, armor = 1),
            Item(name = "Defense +2", cost = 40, damage = 0, armor = 2),
            Item(name = "Defense +3", cost = 80, damage = 0, armor = 3),
        )
    }
}