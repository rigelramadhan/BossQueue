package com.rigelramadhan.bossqueue.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.databinding.ActivityLoginBinding
import com.rigelramadhan.bossqueue.util.LoadingState
import com.rigelramadhan.bossqueue.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    // TODO: COMPLETE THE LOGIN AUTH
    companion object {
        const val TAG = "LoginActivity"
    }

    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        auth = Firebase.auth
        initViewAction()
    }

    private fun initViewAction() {
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            authViewModel.signInWithEmailAndPassword(this, email, password)
        }

        binding.btnCreate.setOnClickListener {
            authViewModel.directToRegister(this)
        }
    }
}