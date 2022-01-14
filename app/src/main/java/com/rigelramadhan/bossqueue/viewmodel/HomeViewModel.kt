package com.rigelramadhan.bossqueue.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.model.Place.Companion.toPlace
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> get() = _loading

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(LoadingState.LOADING)
            val db = Firebase.firestore
            db.collection("places")
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        Log.d(TAG, "Data is empty.")
                    } else {
                        val places = mutableListOf<Place>()
                        for (document in result) {
                            val place = document.toPlace()
                            places.add(place!!)
                        }
                        Log.d(TAG, "Get places data success, total: ${places.size}")
                        _places.postValue(places)
                        _loading.postValue(LoadingState.LOADED)
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "GET PLACES DATA FAILED:\n${it.message}")
                }
        }
    }
}