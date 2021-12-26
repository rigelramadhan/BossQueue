package com.rigelramadhan.bossqueue.viewmodel

import android.util.Log
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
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> get() = _loading

    private val _data = MutableLiveData<List<Place>>()
    val data: LiveData<List<Place>> get() = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(LoadingState.LOADING)
            val checkPlaces = Firebase.database.getReference("places")
            checkPlaces.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val places = mutableListOf<Place>()
                        for (dataSnapshot in snapshot.children) {
                            val place = dataSnapshot.getValue<Place>()
                            if (place != null) {
                                place.id = dataSnapshot.key
                                Log.d(HomeViewModel::class.java.simpleName, "Place ID: ${place.id}")
                                places.add(place)
                            }
                        }
                        _data.postValue(places)
                        _loading.postValue(LoadingState.LOADED)
                    } else {
                        _loading.postValue(LoadingState.error("Snapshot doesn't exist"))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _loading.postValue(LoadingState.error(error.message))
                }
            })
        }
    }
}