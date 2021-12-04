package com.rigelramadhan.bossqueue.view.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.rigelramadhan.bossqueue.R
import com.rigelramadhan.bossqueue.controller.FoodController
import com.rigelramadhan.bossqueue.controller.PlaceController
import com.rigelramadhan.bossqueue.databinding.ActivityMenuBinding
import com.rigelramadhan.bossqueue.model.Category
import com.rigelramadhan.bossqueue.model.Place
import com.rigelramadhan.bossqueue.model.SampleData
import com.rigelramadhan.bossqueue.view.ui.basket.BasketActivity

class MenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PLACE_ID = "extra_place_id"
        private val TAG = MenuActivity::class.java.simpleName
    }

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: ActivityMenuBinding
    private lateinit var foodController: FoodController
    private lateinit var placeController: PlaceController
    private var placeName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodController = FoodController(this)
        placeController = PlaceController(this)

        loadData()
        binding.layoutBasket.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            intent.putExtra(BasketActivity.EXTRA_USER_ID, placeName)
            startActivity(intent)
        }

        binding.rlMenu.setOnClickListener {
            if (SampleData.bill <= 0.0) {
                binding.tvBasket.text = resources.getString(R.string.basket)
            } else {
                binding.tvBasket.text = SampleData.bill.toString()
            }
        }
    }

    private fun loadData() {
        placeName = intent.getStringExtra(EXTRA_PLACE_ID)!!
        Log.d(TAG, "Place ID: $placeName")

        val checkPlaces = Firebase.database.getReference("places").orderByChild("name").equalTo(placeName)
        checkPlaces.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val place = snapshot.children.iterator().next().getValue(Place::class.java)
                    Log.d(TAG, "Place ${place.toString()}")


                    binding.tvPlacename.text = place?.name
                    val storage = FirebaseStorage.getInstance()
                    val gsRef = storage.getReferenceFromUrl(place?.picture!!)

                    gsRef.downloadUrl.addOnSuccessListener {
                        if (!this@MenuActivity.isFinishing) {
                            Glide.with(this@MenuActivity)
                                .load(it)
                                .into(binding.imgPlace)
                        }
                    }

                    foodController.configureFoodsRv(binding.rvFood2, LinearLayoutManager.HORIZONTAL, Category.FOOD)
                    foodController.configureFoodsRv(binding.rvDrink2, LinearLayoutManager.HORIZONTAL, Category.DRINK)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}