package com.rigelramadhan.bossqueue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Food
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(private val id: String) : ViewModel() {
    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> get() = _loading

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(LoadingState.LOADING)
            val checkFoods = Firebase.database.getReference("foods")
            checkFoods.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val foods = mutableListOf<Food>()
                    for (data in snapshot.children) {
                        val food = data.getValue<Food>()
                        if (food != null) {
                            if (food.placeId == id) {
                                food.id = data.key
                                foods.add(food)
                            }
                        }
                    }
                    _foods.postValue(foods)
                    _loading.postValue(LoadingState.LOADED)
                }

                override fun onCancelled(error: DatabaseError) {
                    _loading.postValue(LoadingState.error(error.message))
                }
            })
        }
    }
}