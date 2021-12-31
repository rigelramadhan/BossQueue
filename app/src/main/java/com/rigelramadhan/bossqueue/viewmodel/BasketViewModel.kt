package com.rigelramadhan.bossqueue.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {
    // TODO: STILL UNABLE TO GET FOODS THAT'S ONLY IN THE BASKET
    private val _basket = MutableLiveData<List<Basket>>()
    val basket: LiveData<List<Basket>> = _basket

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val basketQuery = Firebase.database.getReference("baskets")
            basketQuery.orderByChild("user_id").equalTo(FirebaseAuth.getInstance().uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val baskets = mutableListOf<Basket>()
                        for (data in snapshot.children) {
                            val basket = data.getValue<Basket>()
                            if (basket != null) {
                                baskets.add(basket)
                            }
                        }
                        _basket.postValue(baskets)
                        getFoods(baskets)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }

    private fun getFoods(baskets: List<Basket>) {
        viewModelScope.launch(Dispatchers.IO) {
            val checkFoods = Firebase.database.getReference("foods")
            checkFoods.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val foods = mutableListOf<Food>()
                    val ids = mutableListOf<String>()
                    for (basket in baskets) {
                        ids.add(basket.foodId!!)
                    }
                    for (data in snapshot.children) {
                        val food = data.getValue<Food>()
                        if (food != null) {
                            if (food.id in ids) {
                                food.id = data.key
                                foods.add(food)
                            }
                        }
                    }
                    _foods.postValue(foods)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun deleteBasket(userId: String, foodId: String, placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val basketId = "${userId.subSequence(0, 3)}${foodId.subSequence(0, 3)}${placeId.subSequence(0, 3)}"
            Firebase.database.getReference("baskets").child(basketId).removeValue()
        }
    }
}