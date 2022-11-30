package komu.adventofcode.aoc2015

fun scienceForHungryPeople1(ingredients: List<Ingredient>) =
    recipes(ingredients).maxOf { it.score() }

fun scienceForHungryPeople2(ingredients: List<Ingredient>): Int =
    recipes(ingredients).filter { it.calories == 500 }.maxOf { it.score() }

private fun recipes(ingredients: List<Ingredient>) = sequence {
    for (weights in weights(sum = 100))
        yield((weights * ingredients).sum())
}

private fun weights(sum: Int) = sequence {
    for (i in 0..sum)
        for (j in 0..sum - i)
            for (k in 0..sum - i - j)
                yield(listOf(i, j, k, sum - i - j - k))
}

data class Ingredient(
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
) {

    fun score() = capacity * durability * flavor * texture
}

private fun List<Ingredient>.sum() = Ingredient(
    capacity = sumOf { it.capacity }.coerceAtLeast(0),
    durability = sumOf { it.durability }.coerceAtLeast(0),
    flavor = sumOf { it.flavor }.coerceAtLeast(0),
    texture = sumOf { it.texture }.coerceAtLeast(0),
    calories = sumOf { it.calories }.coerceAtLeast(0),
)

private operator fun List<Int>.times(ingredients: List<Ingredient>) =
    zip(ingredients) { w, i -> w * i }

private operator fun Int.times(i: Ingredient) = Ingredient(
    capacity = this * i.capacity,
    durability = this * i.durability,
    flavor = this * i.flavor,
    texture = this * i.texture,
    calories = this * i.calories
)
