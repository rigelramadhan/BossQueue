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
import com.rigelramadhan.bossqueue.repository.PlaceRepository
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> get() = _loading

    private val _places = PlaceRepository.getPlaces()
    val places: LiveData<List<Place>> get() = _places

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    init {

    }
}