package com.rigelramadhan.bossqueue.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Basket.Companion.toBasket
import com.rigelramadhan.bossqueue.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BasketRepository {
    // TODO: FIX THE DELETE BASKET CODE
    private val mBasket = MutableLiveData<List<Basket>>()
    private const val TAG = "BasketRepository"

    init {
        val db = Firebase.firestore
        db.collection("baskets")
            .get()
            .addOnSuccessListener { result ->
                val baskets = mutableListOf<Basket>()
                for (document in result) {
                    if (document["userId"] == FirebaseAuth.getInstance().uid) {
                        val basket = document.toBasket()
                        baskets.add(basket!!)
                    }
                }
                mBasket.postValue(baskets)
            }
    }

    fun getBasket() = mBasket

    fun getBasketByPlaceId(placeId: String): List<Basket> {
        val baskets = mutableListOf<Basket>()

        for (i in mBasket.value!!) {
            if (i.placeId == placeId) {
                baskets.add(i)
            }
        }

        return baskets
    }

    fun deleteBasket(basketId: String) {
        val db = Firebase.firestore
        db.collection("baskets").document(basketId).delete()
            .addOnCompleteListener {
                val baskets = mBasket.value as MutableList<Basket>
            }
    }

    fun deleteBasketByFoodId(foodId: String) {
        Log.d(TAG, "DELETING BASKET WITH FOOD: $foodId")
        val db = Firebase.firestore
        for (i in mBasket.value!!) {
            if (i.foodId == foodId && i.userId == FirebaseAuth.getInstance().uid) {
                db.collection("baskets").document(i.basketId!!).delete()
                    .addOnCompleteListener {
                        val baskets = mBasket.value as MutableList<Basket>
                        baskets.remove(i)
                        mBasket.value = baskets
                        Log.d(TAG, "Food has been deleted, Basket ID: ${i.basketId}")
                    }
            }
        }
    }

    fun checkBasketAvailability(foodId: String) : Boolean {
        Log.d(TAG, "Baskets: ${mBasket.value}")
        for (i in mBasket.value!!) {
            Log.d(TAG, "Food: ${i.foodId} <<<>>> $foodId")
            if (i.foodId == foodId) {
                Log.d(TAG, "RETURNING TRUE")
                return true
            }
        }

        return false
    }

    fun getTotalPrice(placeId: String): Double {
        var totalPrice = 0.0

        for (i in mBasket.value!!) {
            if (i.placeId == placeId) {
                totalPrice += FoodRepository.getFoodsByBasket(i)?.price!!
            }
        }

        return totalPrice
    }
}