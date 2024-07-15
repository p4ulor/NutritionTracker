package p4ulor.nutritiontracker.controllers

import p4ulor.nutritiontracker.objs.Food
import p4ulor.nutritiontracker.objs.Nutrients
import java.util.HashMap

class DailyTracker {
    private val nutrients: Array<Nutrients> = Nutrients.values()
    val currentDailyNutrition = Food("currentDailyNutrition", HashMap())
}