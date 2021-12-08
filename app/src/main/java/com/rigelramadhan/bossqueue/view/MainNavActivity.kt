package com.rigelramadhan.bossqueue.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rigelramadhan.bossqueue.R
import com.rigelramadhan.bossqueue.databinding.ActivityMainNavBinding
import com.rigelramadhan.bossqueue.viewmodel.AuthViewModel

class MainNavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainNavBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main_nav) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        authViewModel.checkIfUserSignedIn(this)
    }
}