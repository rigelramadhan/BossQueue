package com.rigelramadhan.bossqueue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Basket.Companion.toBasket
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.Food.Companion.toFood
import com.rigelramadhan.bossqueue.repository.BasketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {
    // TODO: STILL UNABLE TO GET FOODS THAT'S ONLY IN THE BASKET
    val basket: LiveData<List<Basket>> = BasketRepository.getBasket()

    fun checkBucketAvailability(foodId: String) : Boolean {
        for (i in this.basket.value!!) {
            if (i.foodId == foodId) {
                return true
            }
        }

        return false
    }

    fun deleteBasket(userId: String, foodId: String, placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val basketId = "${userId.subSequence(0, 3)}${foodId.subSequence(0, 3)}${placeId.subSequence(0, 3)}"
            Firebase.database.getReference("baskets").child(basketId).removeValue()
        }
    }
}