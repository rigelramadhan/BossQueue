package com.rigelramadhan.bossqueue.view.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rigelramadhan.bossqueue.adapter.FoodAdapter
import com.rigelramadhan.bossqueue.databinding.ActivityMenuBinding
import com.rigelramadhan.bossqueue.model.SampleData
import com.rigelramadhan.bossqueue.view.ui.basket.BasketActivity

class MenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PLACE_ID = "extra_place_id"
    }

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: ActivityMenuBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        binding.layoutBasket.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            intent.putExtra(BasketActivity.EXTRA_USER_ID, userId)
            startActivity(intent)
        }
    }

    private fun loadData() {
        userId = intent.getIntExtra(EXTRA_PLACE_ID, 1)
        menuViewModel = MenuViewModel(userId)
        val foods = menuViewModel.getFoods(1)
        val drinks = menuViewModel.getFoods(2)
        val place = menuViewModel.getPlace()

        binding.tvPlacename.text = place.name
        binding.rvFood2.apply {
            adapter = FoodAdapter(foods)
        }

        binding.rvDrink2.apply {
            adapter = FoodAdapter(drinks)
        }
    }
}