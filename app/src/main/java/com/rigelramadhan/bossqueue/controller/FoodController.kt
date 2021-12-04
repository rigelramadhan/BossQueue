package com.rigelramadhan.bossqueue.controller

import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.adapter.FoodAdapter
import com.rigelramadhan.bossqueue.model.Category
import com.rigelramadhan.bossqueue.model.Food

class FoodController(private val activity: AppCompatActivity) {

    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference
    private var foods = mutableListOf<Food>()

    companion object {
        private val TAG = FoodController::class.java.simpleName
    }

//    init {
//        addSampleFoods()
//    }

    public fun configureFoodsRv(rv: RecyclerView, orientation: Int, categoryId: Int = 0, basketText: TextView? = null) {
        val checkFoods = Firebase.database.getReference("foods")

        checkFoods.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val tempFoods = mutableListOf<Food>()
                    for (dataSnapshot in snapshot.children) {
                        val food = dataSnapshot.getValue<Food>()
                        Log.d(FoodController.TAG, food.toString())
                        if (food != null) {
                            if (categoryId != 0) {
                                if (food.categoryId == categoryId) {
                                    tempFoods.add(food)
                                }
                            } else {
                                tempFoods.add(food)
                            }
                        } else {
                            Log.d(FoodController.TAG, "PLACE NULL")
                        }
                    }
                    Log.d(FoodController.TAG, tempFoods.toString())
                    if (categoryId == 0) foods = tempFoods
                    rv.apply {
                        adapter = FoodAdapter(activity, tempFoods, basketText)
                        layoutManager = LinearLayoutManager(context, orientation, false)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    public fun addFood(name: String, description: String, price: Double, picture: String, categoryId: Int, placeId: String) {
        val id = "$name${(Math.random() * 1000000).toInt()}"
        val food = Food(name, description, price, picture, categoryId, placeId)
        database.child("foods").child(id).setValue(food)
    }

    private fun addSampleFoods() {
        addFood("Latte", "A refreshing espresso with delicious milk", 25000.00
            , "gs://bossqueue-c992e.appspot.com/foods/Mask Group.png", Category.DRINK, "Arborea Cafe269320")

        addFood("Salad", "A healthy food that's also delicious", 32500.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 24.png", Category.FOOD, "Hause Rooftop37673")

        addFood("Mushroom Toast", "A delicious toast with mushroom on top", 35000.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 25.png", Category.FOOD, "Hause Rooftop37673")

        addFood("Healthy Bowl", "A healthy meal packaged in a bowl", 47500.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 26.png", Category.FOOD, "Subway283026")

        addFood("Folded Pizza", "A pizza folded in half", 82500.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 28.png", Category.FOOD, "Burger King528685")

        addFood("Matcha Latte", "Latte with matcha flavour", 56000.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 29.png", Category.DRINK, "Starbucks37832")

        addFood("Hause Drink", "A special drink from Hause Rooftop", 28000.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 30.png", Category.DRINK, "Hause Rooftop37673")

        addFood("Special Milk", "A special milk from the king of burgers", 13000.00
            , "gs://bossqueue-c992e.appspot.com/foods/image 31 (1).png", Category.DRINK, "Burger King528685")
    }
}