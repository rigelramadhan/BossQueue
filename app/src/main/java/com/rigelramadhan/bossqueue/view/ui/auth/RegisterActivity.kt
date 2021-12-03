package com.rigelramadhan.bossqueue.view.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rigelramadhan.bossqueue.controller.AuthController
import com.rigelramadhan.bossqueue.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authController = AuthController(this)
        initViewAction()
    }

    private fun initViewAction() {
        binding.btnCreate.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()

            authController.signUpWithEmailAndPassword(name, email, password, "Malang")
        }
    }
}