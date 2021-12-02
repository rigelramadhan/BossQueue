package com.rigelramadhan.bossqueue.view.ui.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.bossqueue.adapter.RemoveFoodAdapter
import com.rigelramadhan.bossqueue.databinding.ActivityBasketBinding

class BasketActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }

    private lateinit var basketViewModel: BasketViewModel
    private lateinit var binding: ActivityBasketBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
    }

    private fun loadData() {
        userId = intent.getIntExtra(EXTRA_USER_ID, 1)
        basketViewModel = BasketViewModel(userId)
        binding.rvFoods.apply {
            adapter = RemoveFoodAdapter(basketViewModel.getFoods())
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}