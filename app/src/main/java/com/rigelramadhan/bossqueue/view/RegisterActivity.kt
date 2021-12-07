package com.rigelramadhan.bossqueue.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rigelramadhan.bossqueue.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    // TODO: COMPLETE THE REGISTER
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewAction()
    }

    private fun initViewAction() {
        binding.btnCreate.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
        }
    }
}