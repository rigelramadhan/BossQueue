package com.rigelramadhan.bossqueue.viewmodel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.User
import com.rigelramadhan.bossqueue.util.LoadingState
import com.rigelramadhan.bossqueue.view.LoginActivity
import com.rigelramadhan.bossqueue.view.MainNavActivity
import com.rigelramadhan.bossqueue.view.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState> = _loading

    companion object {
        private const val TAG = "AuthViewModel"
    }

    fun checkIfUserSignedIn(activity: AppCompatActivity) {
        viewModelScope.launch(Dispatchers.Main) {
            _loading.postValue(LoadingState.LOADING)
            val currentUser = auth.currentUser
            if (currentUser == null) {
                _loading.postValue(LoadingState.LOADED)
                directToLogin(activity)
            }
            _loading.postValue(LoadingState.LOADED)
        }
    }

    fun signInWithEmailAndPassword(activity: AppCompatActivity, email: String, password: String) {
        _loading.postValue(LoadingState.LOADING)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    _loading.postValue(LoadingState.LOADED)
                    directToMainMenu(activity)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    _loading.postValue(LoadingState.error(task.exception?.message))
                }
            }
    }

    fun signUpWithEmailAndPassword(activity: AppCompatActivity, name: String, email: String, password: String, location: String) {
        _loading.postValue(LoadingState.LOADING)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        createUser(user.uid, name, email, password, location)
                        _loading.postValue(LoadingState.LOADED)
                    }
                    directToLogin(activity)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    _loading.postValue(LoadingState.error(task.exception?.message))
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

    fun createUser(userId: String, name: String, email: String, password: String, location: String) {
        val user = User(null, name, email, password, location)
        database.child("users").child(userId).setValue(user)
    }

    private fun directToMainMenu(activity: AppCompatActivity) {
        val intent = Intent(activity, MainNavActivity::class.java)
        activity.startActivity(intent)
    }

    private fun directToLogin(activity: AppCompatActivity) {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    public fun directToRegister(activity: AppCompatActivity) {
        val intent = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(intent)
    }
}