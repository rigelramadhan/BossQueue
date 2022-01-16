package com.rigelramadhan.bossqueue.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.Food.Companion.toFood

object FoodRepository {
    private val mFoods = MutableLiveData<List<Food>>()
    private const val TAG = "FoodRepository"

    init {
        val db = Firebase.firestore
        db.collection("foods")
            .get()
            .addOnSuccessListener { result ->
                val foods = mutableListOf<Food>()
                for (document in result) {
                    val food = document.toFood()
                    foods.add(food!!)
                }
                mFoods.postValue(foods)
            }
    }

    fun getFoods() = mFoods

    fun getFilteredFoods(foods: List<Food>, categoryId: Int): List<Food> {
        val newFoods = mutableListOf<Food>()
        for (i in foods) {
            if (i.categoryId == categoryId) {
                newFoods.add(i)
            }
        }

        return newFoods
    }

    fun getFoodsByPlaceId(placeId: String): List<Food> {
        // TODO: FIX GET FOOD BY ID INEFFICIENCY
        val db = Firebase.firestore
        val foods = mutableListOf<Food>()

        for (i in mFoods.value!!) {
            if (i.placeId == placeId) {
                foods.add(i)
            }
        }

        Log.d(TAG, "Foods: $foods")
        return foods
    }

    fun getFoodsByBasket(basket: Basket): Food? {
        for (i in mFoods.value!!) {
            if (i.id == basket.foodId) {
                return i
            }
        }

        return null
    }
}