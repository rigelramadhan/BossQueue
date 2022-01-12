package com.rigelramadhan.bossqueue.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Basket
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(private val placeId: String) : ViewModel() {
    private val db = Firebase.firestore

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> get() = _loading

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    private val _place = MutableLiveData<Place>()
    val place: LiveData<Place> = _place

    private val _basket = MutableLiveData<List<Basket>>()
    val basket: LiveData<List<Basket>> = _basket

    companion object {
        private val TAG = MenuViewModel::class.java.simpleName
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        Log.d(TAG, "PlaceID: $placeId")
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(LoadingState.LOADING)
            db.collection("foods")
                .get()
                .addOnSuccessListener { result ->
                    val foods = mutableListOf<Food>()
                    for (document in result) {
                        if (document.get("placeId") == placeId) {
                            Log.d(TAG, "Food: $document")
                            val food = document.toObject(Food::class.java)
                            food.id = document.id
                        }
                    }
                    _foods.postValue(foods)
                    _loading.postValue(LoadingState.LOADED)
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.collection("places")
                .document(placeId)
                .get()
                .addOnSuccessListener { result ->
                    val place = result.toObject(Place::class.java)
                    place!!.id = result.id
                    _place.postValue(place!!)
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.collection("baskets")
                .get()
                .addOnSuccessListener { result ->
                    val baskets = mutableListOf<Basket>()
                    for (document in result) {
                        if (document.get("placeId") == placeId &&
                            document.get("userId") == FirebaseAuth.getInstance().uid) {

                            val basket = document.toObject(Basket::class.java)
                            baskets.add(basket)
                        }
                    }
                }
        }
    }

    fun createBasket(userId: String, foodId: String, placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val basketId = "${userId.subSequence(0, 3)}${foodId.subSequence(0, 3)}${placeId.subSequence(0, 3)}"

            val basket = hashMapOf(
                "userId" to userId,
                "foodId" to foodId,
                "placeId" to placeId
            )

            db.collection("baskets")
                .document(basketId)
                .set(basket)
                .addOnSuccessListener {
                    Log.d(TAG, "Data set completed, data: $basket")
                }
        }
    }

    fun deleteBasket(userId: String, foodId: String, placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val basketId = "${userId.subSequence(0, 3)}${foodId.subSequence(0, 3)}${placeId.subSequence(0, 3)}"
            db.collection("baskets").document(basketId).delete()
        }
    }

    class MenuViewModelFactory(private val placeId: String = "0") : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MenuViewModel(placeId) as T
        }
    }
}