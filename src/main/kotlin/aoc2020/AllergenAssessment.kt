package aoc2020

import komu.adventofcode.utils.nonEmptyLines

fun allergenAssessment1(input: String): Int {
    val foods = input.nonEmptyLines().map { Food.parse(it) }
    val ingredientsWithAllergens = ingredientsByAllergens(foods).flatMap { it.value }

    val safe = foods.flatMap { it.ingredients }.toSet() - ingredientsWithAllergens

    return foods.sumBy { food -> food.ingredients.count { it in safe } }
}

fun allergenAssessment2(input: String): String {
    val foods = input.nonEmptyLines().map { Food.parse(it) }
    val ingredientsByAllergens = ingredientsByAllergens(foods).mapValues { it.value.toMutableSet() }.toMutableMap()

    val result = sortedMapOf<String, String>()

    while (true) {
        val (allergen, ingredients) = ingredientsByAllergens.entries.find { it.value.size == 1 } ?: break
        val ingredient = ingredients.single()
        ingredientsByAllergens.remove(allergen)

        for (allergens in ingredientsByAllergens.values)
            allergens.remove(ingredient)

        result[allergen] = ingredient
    }

    return result.values.joinToString(",")
}

private fun ingredientsByAllergens(foods: List<Food>): Map<String, Set<String>> {
    val ingredients = foods.flatMap { it.ingredients }.toSet()
    val allergens = foods.flatMap { it.allergens }.toSet()

    val candidateAllergens = allergens.associateWith { ingredients.toMutableSet() }

    for (food in foods)
        for (allergen in food.allergens)
            candidateAllergens[allergen]?.retainAll(food.ingredients)

    return candidateAllergens
}

private data class Food(val ingredients: Set<String>, val allergens: Set<String>) {
    companion object {

        private val regex = Regex("""(.+) \(contains (.+)\)""")

        fun parse(s: String): Food {
            val (_, ingredients, allergens) = regex.matchEntire(s)?.groupValues ?: error("invalid line '$s'")

            return Food(ingredients.split(' ').toSet(), allergens.split(Regex(", ")).toSet())
        }
    }
}
