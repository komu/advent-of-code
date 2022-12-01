package komu.adventofcode.aoc2015

import utils.shortestPathWithCost

fun wizardSimulator(hard: Boolean = false): Int {
    val initial = WizardSimulatorState(
        bossHitPoints = 55,
        wizardHitPoints = 50,
        wizardMana = 500,
        effects = emptyList(),
        hard = hard
    )

    return shortestPathWithCost(initial, WizardSimulatorState::isWin, WizardSimulatorState::validTransitions)?.second
        ?: error("no path")
}

private data class WizardSimulatorState(
    private val bossHitPoints: Int,
    private val wizardHitPoints: Int,
    private val wizardMana: Int,
    private val effects: List<Effect>,
    private val hard: Boolean
) {

    val isWin: Boolean
        get() = bossHitPoints <= 0 && wizardHitPoints > 0

    private val isOver: Boolean
        get() = bossHitPoints <= 0 || wizardHitPoints <= 0

    fun validTransitions(): List<Pair<WizardSimulatorState, Int>> {
        val state = applyPreTurnEffects(wizardTurn = true)
        return if (state.isOver)
            emptyList()
        else
            Spell.values().filter { state.mayCast(it) }.map { spell ->
                Pair(state.cast(spell).handleBossTurn(), spell.cost)
            }
    }

    private fun handleBossTurn(): WizardSimulatorState {
        val s = applyPreTurnEffects(wizardTurn = false)
        if (s.isOver) return s

        val armor = if (s.isActive(EffectType.SHIELD)) 7 else 0
        return s.copy(
            wizardHitPoints = s.wizardHitPoints - (8 - armor).coerceAtLeast(1),
        )
    }

    private fun applyPreTurnEffects(wizardTurn: Boolean) = copy(
        bossHitPoints = bossHitPoints - if (isActive(EffectType.POISON)) 3 else 0,
        wizardMana = wizardMana + if (isActive(EffectType.RECHARGE)) 101 else 0,
        effects = effects.mapNotNull { it.decrease() },
        wizardHitPoints = wizardHitPoints - if (hard && wizardTurn) 1 else 0
    )

    private fun cast(spell: Spell) = copy(
        bossHitPoints = bossHitPoints - spell.damage,
        wizardHitPoints = wizardHitPoints + spell.heal,
        wizardMana = wizardMana - spell.cost,
        effects = if (spell.effect != null) effects + Effect(spell.effect) else effects
    )

    private fun mayCast(spell: Spell) =
        wizardMana >= spell.cost && (spell.effect == null || !isActive(spell.effect))

    private fun isActive(effect: EffectType) =
        effects.any { it.type == effect }
}

private enum class Spell(val cost: Int, val damage: Int = 0, val heal: Int = 0, val effect: EffectType? = null) {
    MAGIC_MISSILE(cost = 53, damage = 4),
    DRAIN(cost = 73, damage = 2, heal = 2),
    SHIELD(cost = 113, effect = EffectType.SHIELD),
    POISON(cost = 173, effect = EffectType.POISON),
    RECHARGE(cost = 229, effect = EffectType.RECHARGE);
}

private enum class EffectType(val turns: Int) {
    SHIELD(turns = 6), POISON(turns = 6), RECHARGE(turns = 5);
}

private data class Effect(val type: EffectType, val turns: Int = type.turns) {
    fun decrease() =
        if (turns == 1) null else Effect(type, turns - 1)
}
