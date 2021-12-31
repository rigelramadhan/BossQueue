package com.rigelramadhan.bossqueue.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(private val placeId: String) : ViewModel() {
    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> get() = _loading

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    private val _place = MutableLiveData<Place>()
    val place: LiveData<Place> = _place

    private val _basket = MutableLiveData<List<Basket>>()
    val basket: LiveData<List<Basket>> = _basket

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(LoadingState.LOADING)
            val checkFoods = Firebase.database.getReference("foods")
            Log.d(MenuViewModel::class.java.simpleName, "Foods database available, Place ID: $placeId")
            checkFoods.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val foods = mutableListOf<Food>()
                    for (data in snapshot.children) {
                        val food = data.getValue<Food>()
                        if (food != null) {
                            if (food.placeId == placeId) {
                                food.id = data.key
                                Log.d(MenuViewModel::class.java.simpleName, "Food: $food")
                                foods.add(food)
                            }
                        }
                    }
                    Log.d(MenuViewModel::class.java.simpleName, "Foods: ${foods.toString()}")
                    _foods.postValue(foods)
                    _loading.postValue(LoadingState.LOADED)
                }

                override fun onCancelled(error: DatabaseError) {
                    _loading.postValue(LoadingState.error(error.message))
                }
            })
        }

        viewModelScope.launch(Dispatchers.IO) {
            val queryPlace = Firebase.database.getReference("places")
            queryPlace.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        if (data.key == placeId) {
                            val place = data.getValue<Place>()
                            place!!.id = data.key
                            _place.postValue(place!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        viewModelScope.launch(Dispatchers.IO) {
            val queryBasket = Firebase.database.getReference("basket")
            queryBasket.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val basketList = mutableListOf<Basket>()
                    for (data in snapshot.children) {
                        if (data.key == UserViewModel.getUser()?.id) {
                            val basket = data.getValue<Basket>()
                            basket!!.userId = data.key
                            basketList.add(basket)
                        }
                    }
                    _basket.postValue(basketList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

    fun createBasket(userId: String, foodId: String, placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val basketId = "${userId.subSequence(0, 3)}${foodId.subSequence(0, 3)}${placeId.subSequence(0, 3)}"
            Firebase.database.reference.child("baskets").child(basketId)
                .child("user_id").setValue(userId)
            Firebase.database.reference.child("baskets").child(basketId)
                .child("food_id").setValue(foodId)
            Firebase.database.reference.child("baskets").child(basketId)
                .child("place_id").setValue(placeId)
        }
    }

    fun deleteBasket(userId: String, foodId: String, placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val basketId = "${userId.subSequence(0, 3)}${foodId.subSequence(0, 3)}${placeId.subSequence(0, 3)}"
            Firebase.database.getReference("baskets").child(basketId).removeValue()
        }
    }

    class MenuViewModelFactory(private val placeId: String = "0") : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MenuViewModel(placeId) as T
        }
    }
}