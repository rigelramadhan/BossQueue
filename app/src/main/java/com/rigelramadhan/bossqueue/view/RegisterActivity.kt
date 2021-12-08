package com.rigelramadhan.bossqueue.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rigelramadhan.bossqueue.databinding.ActivityRegisterBinding
import com.rigelramadhan.bossqueue.util.LoadingState
import com.rigelramadhan.bossqueue.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.loading.observe(this, {
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

        initViewAction()
    }

    private fun initViewAction() {
        binding.btnCreate.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            authViewModel.signUpWithEmailAndPassword(this, name, email, password, "Malang")
        }
    }
}