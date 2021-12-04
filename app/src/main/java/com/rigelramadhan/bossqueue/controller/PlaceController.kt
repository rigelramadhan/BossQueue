package com.rigelramadhan.bossqueue.controller

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.adapter.PlaceAdapter
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.model.User

class PlaceController(private val activity: AppCompatActivity) {
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference
    private var places = mutableListOf<Place>()

    companion object {
        private val TAG = PlaceController::class.java.simpleName
    }

    public fun addPlace(name: String, location: String, description: String, open: Boolean, picture: String) {
        val id = "$name${(Math.random() * 1000000).toInt()}"
        val place = Place(name, location, description, open, picture)
        database.child("places").child(id).setValue(place)
    }

    public fun configurePlacesRv(rv: RecyclerView, orientation: Int) {
        val checkPlaces = Firebase.database.getReference("places")

        checkPlaces.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val tempPlaces = mutableListOf<Place>()
                    for (dataSnapshot in snapshot.children) {
                        val place = dataSnapshot.getValue<Place>()
                        Log.d(TAG, place.toString())
                        if (place != null) {
                            tempPlaces.add(place)
                        } else {
                            Log.d(TAG, "PLACE NULL")
                        }
                    }
                    Log.d(TAG, tempPlaces.toString())
                    places = tempPlaces
                    rv.apply {
                        adapter = PlaceAdapter(activity, places)
                        layoutManager = LinearLayoutManager(context, orientation, false)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    public fun configureNearPlacesRv(rv: RecyclerView, orientation: Int) {
        val checkPlaces = Firebase.database.getReference("places")
        val userData = Firebase.database.getReference("users").child(auth.uid.toString())
        var user = User()

        userData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue<User>()!!
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        checkPlaces.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val tempPlaces = mutableListOf<Place>()
                    for (dataSnapshot in snapshot.children) {
                        val place = dataSnapshot.getValue<Place>()
                        Log.d(TAG, place.toString())
                        if (place != null) {
                            if (place.location.equals(user.location)) tempPlaces.add(place)
                        } else {
                            Log.d(TAG, "PLACE NULL")
                        }
                    }
                    Log.d(TAG, tempPlaces.toString())
                    places = tempPlaces
                    rv.apply {
                        adapter = PlaceAdapter(activity, places)
                        layoutManager = LinearLayoutManager(context, orientation, false)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    public fun getParent(name: String): String {
        val checkPlaces = Firebase.database.getReference("places")
        var key = ""
        checkPlaces.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    if (data.child("name").toString().equals(name, true)) {
                        key = data.key.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })

        return key
    }

    public fun getPlaces() = places

    private fun addSamplePlaces() {
        addPlace("Hause Rooftop", "Malang"
            , "Hause is a haven situated in the heart of buzzing Jakarta. It is a cozy nook where you can unwind and let the wind sway you into a dimension of comfort food and smiling faces. Witness the regal sun journey across Jakartas skyline from our outdoor backyard and taste the cotton candy skies as you sip our finest brewed beverages. Hause is not merely about food, its about a lifestyle. It resides within our hearts. Home is where the Heart is, but Happiness is Hausemade."
            , true
            , ""
        )
        addPlace("Starbucks", "Malang"
            , "Starbucks uses the highest quality arabica coffee as the base for its beloved drinks."
            , true
            , ""
        )
        addPlace("Subway", "Malang"
            , "Subway is an American multi-national fast food restaurant franchise that primarily sells submarine sandwiches, wraps, salads and beverages."
            , true
            , ""
        )
        addPlace("Arborea Cafe", "Malang"
            , "Cozy cafe with outdoor seating in a tree-shaded green space offering elevated local fare & coffee."
            , true
            , ""
        )
        addPlace("Burger King", "Malang"
            , "Burger King is an American multinational chain of hamburger fast food restaurants."
            , true
            , ""
        )

    }
}