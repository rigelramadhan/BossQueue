package com.rigelramadhan.bossqueue.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rigelramadhan.bossqueue.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    // TODO: COMPLETE THE MENU
    companion object {
        const val EXTRA_PLACE_ID = "extra_place_id"
        private val TAG = MenuActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMenuBinding
    private var placeName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutBasket.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            intent.putExtra(BasketActivity.EXTRA_USER_ID, placeName)
            startActivity(intent)
        }
    }
}