package p4ulor.nutritiontracker.controllers

import p4ulor.nutritiontracker.objs.Food
import p4ulor.nutritiontracker.objs.Meal

interface DailyTrackerOperations {
    fun addFood(food: Food)
    fun addMeal(meal: Meal)
    fun editFoodQuantity(food: Food, newQuantity: Float)
    fun editMealQuantity(meal: Meal, newQuantity: Float)
    fun removeFood(food: Food)
    fun removeMeal(meal: Meal)
    fun saveFoodsForToday()
    fun saveMealsForToday()
}