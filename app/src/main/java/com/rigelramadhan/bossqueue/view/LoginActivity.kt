package com.rigelramadhan.bossqueue.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    // TODO: COMPLETE THE LOGIN AUTH
    companion object {
        const val TAG = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        initViewAction()
    }

    private fun initViewAction() {
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
        }

        binding.btnCreate.setOnClickListener {
        }
    }
}