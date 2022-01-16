package com.rigelramadhan.bossqueue.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.rigelramadhan.bossqueue.adapter.FoodAdapter
import com.rigelramadhan.bossqueue.databinding.ActivityMenuBinding
import com.rigelramadhan.bossqueue.util.LoadingState
import com.rigelramadhan.bossqueue.viewmodel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PLACE_ID = "extra_place_id"
//        private val TAG = this::class.java.simpleName
    }

    private lateinit var binding: ActivityMenuBinding
    private lateinit var placeId: String
    val menuViewModel: MenuViewModel by viewModels {
        val placeId = intent.getStringExtra(EXTRA_PLACE_ID)
        if (placeId != null) {
            this.placeId = placeId
            MenuViewModel.MenuViewModelFactory(placeId)
        } else MenuViewModel.MenuViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuViewModel.foods.observe(this, {
            binding.rvFood2.apply {
                adapter = FoodAdapter(this@MenuActivity, it)
                layoutManager = LinearLayoutManager(this@MenuActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        })

        menuViewModel.drinks.observe(this, {
            binding.rvDrink2.apply {
                adapter = FoodAdapter(this@MenuActivity, it)
                layoutManager = LinearLayoutManager(this@MenuActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        })

        menuViewModel.loading.observe(this, {
            if (it.status == LoadingState.Status.RUNNING) {
                binding.progressBar.visibility = View.VISIBLE
            }
            if (it.status == LoadingState.Status.SUCCESS) {
                binding.progressBar.visibility = View.INVISIBLE
            }
            if (it.status == LoadingState.Status.FAILED) {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        menuViewModel.place.observe(this, { place ->
            binding.tvPlaceName.text = place.name
            Log.d("MenuActivity-PlaceObs", "Place: ${place.name}")

            val storage = FirebaseStorage.getInstance()
            val gsRef = storage.getReferenceFromUrl(place.picture!!)

            gsRef.downloadUrl.addOnSuccessListener { uri ->
                if (!this@MenuActivity.isFinishing) {
                    Glide.with(applicationContext)
                        .load(uri)
                        .apply(RequestOptions().override(720))
                        .into(binding.imgPlace)
                }
            }

            binding.layoutBasket.setOnClickListener {
                val intent = Intent(this, BasketActivity::class.java)
                intent.putExtra(BasketActivity.EXTRA_USER_ID, place.name)
                intent.putExtra(BasketActivity.EXTRA_PLACE_ID, placeId)
                startActivity(intent)
            }
        })
    }
}