package com.rigelramadhan.bossqueue.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.model.Place.Companion.toPlace

object PlaceRepository {
    private val mPlaces = MutableLiveData<List<Place>>()
    private const val TAG = "PlaceRepository"

    init {
        val db = Firebase.firestore
        db.collection("places")
            .get()
            .addOnSuccessListener { result ->
                val places = mutableListOf<Place>()
                for (document in result) {
                    places.add(document.toPlace()!!)
                }
                mPlaces.postValue(places)
            }
    }

    fun getPlaces() = mPlaces

    fun getPlaceById(placeId: String): Place? {
        for (i in mPlaces.value!!) {
            if (i.id == placeId) {
                return i
            }
        }

        return null
    }
}