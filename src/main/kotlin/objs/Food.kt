package p4ulor.nutritiontracker.objs

data class Food(
    val name: String,
    val nutrients: HashMap<Nutrients, Float>,
) {
    fun sortNutrients() = nutrients.toSortedMap(Nutrients.nutrientComparator)
}

