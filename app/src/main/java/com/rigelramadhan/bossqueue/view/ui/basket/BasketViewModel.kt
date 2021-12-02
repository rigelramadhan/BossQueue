package com.rigelramadhan.bossqueue.view.ui.basket

import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.SampleData

class BasketViewModel(private val userId: Int) {
    private val foods = mutableListOf<Food>()

    init {
        loadData()
    }

    private fun loadData() {
        val baskets = SampleData.basketSampleData

        for (basket in baskets) {
            if (basket.userId == userId) {
                foods.add(SampleData.foodSampleData[basket.foodId])
            }
        }
    }

    fun getFoods() = foods
}