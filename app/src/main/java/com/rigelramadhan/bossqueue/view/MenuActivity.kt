package com.rigelramadhan.bossqueue.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rigelramadhan.bossqueue.databinding.ActivityMenuBinding
import com.rigelramadhan.bossqueue.viewmodel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    // TODO: COMPLETE THE MENU
    companion object {
        const val EXTRA_PLACE_ID = "extra_place_id"
        private val TAG = this::class.java.simpleName
    }

    private lateinit var binding: ActivityMenuBinding
    private var placeName = ""
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: CALL THE VIEWMODEL
        menuViewModel.foods.observe(this, {

        })

        binding.layoutBasket.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            intent.putExtra(BasketActivity.EXTRA_USER_ID, placeName)
            startActivity(intent)
        }
    }
}