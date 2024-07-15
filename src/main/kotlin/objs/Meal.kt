package p4ulor.nutritiontracker.objs

data class Meal(
    val name: String,
    val foods: HashMap<Food, Float>,
)
