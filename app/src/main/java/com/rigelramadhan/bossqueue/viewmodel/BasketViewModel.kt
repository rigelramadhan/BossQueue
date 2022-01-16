package com.rigelramadhan.bossqueue.viewmodel

import androidx.lifecycle.*
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

class BasketViewModel(private val placeId: String) : ViewModel() {
    // TODO: STILL UNABLE TO GET FOODS THAT'S ONLY IN THE BASKET
    private val _baskets = MutableLiveData<List<Basket>>()
    val basket: LiveData<List<Basket>> = _baskets

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _baskets.postValue(BasketRepository.getBasketByPlaceId(placeId))
        }
    }

    class BasketViewModelFactory(private val placeId: String = "0") : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BasketViewModel(placeId) as T
        }
    }

}