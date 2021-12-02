package com.rigelramadhan.bossqueue.view.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.model.SampleData

class MenuViewModel(private val id: Int) {
    private val place: Place = SampleData.placeSampleData[id]
    private val foods = mutableListOf<Food>()

    init {
        loadFoods()
    }

    private fun loadFoods() {
        val allFoods = SampleData.foodSampleData

        for (food in allFoods) {
            if (food.placeId == id) {
                foods.add(food)
            }
        }
    }

    fun getFoods(categoryId: Int): MutableList<Food> {
        val foods = mutableListOf<Food>()

        if (categoryId == 1) {
            for (food in this.foods) {
                if (food.categoryId == categoryId) {
                    foods.add(food)
                }
            }
        }

        return foods
    }
    fun getPlace() = place
}