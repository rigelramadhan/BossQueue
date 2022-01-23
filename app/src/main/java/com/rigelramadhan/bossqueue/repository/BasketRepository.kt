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
import com.rigelramadhan.bossqueue.viewmodel.MenuViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BasketRepository {
    // TODO: FIX THE DELETE BASKET CODE
    private val db = Firebase.firestore.collection("baskets")
    private val mBasket = MutableLiveData<List<Basket>>()
    private const val TAG = "BasketRepository"

    init {
        db.get().addOnSuccessListener { result ->
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

    fun createBasket(userId: String, foodId: String, placeId: String) {
        val basket = hashMapOf(
            "userId" to userId,
            "foodId" to foodId,
            "placeId" to placeId
        )

        db.add(basket).addOnSuccessListener {
                Log.d(TAG, "Data set completed, data: $basket")
                val basketData = Basket(it.id, basket["userId"], basket["placeId"], basket["foodId"])
                val baskets = mBasket.value as MutableList<Basket>
                baskets.add(basketData)
                mBasket.postValue(baskets)
            }
    }

    fun getBasket() = mBasket

    fun getBasketByPlaceId(placeId: String): List<Basket> {
        val baskets = mutableListOf<Basket>()

        for (i in mBasket.value!!) {
            if (i.placeId == placeId && i.userId == FirebaseAuth.getInstance().uid) {
                baskets.add(i)
            }
        }

        return baskets
    }

    fun deleteBasket(basketId: String) {
        db.document(basketId).delete()
            .addOnCompleteListener {
                mBasket.value as MutableList<Basket>
            }
    }

    fun deleteBasketByFoodId(foodId: String) {
        Log.d(TAG, "DELETING BASKET WITH FOOD: $foodId")
        for (i in mBasket.value!!) {
            if (i.foodId == foodId && i.userId == FirebaseAuth.getInstance().uid) {
                db.document(i.basketId!!).delete()
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