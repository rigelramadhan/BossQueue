package com.rigelramadhan.bossqueue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val auth: FirebaseAuth) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    companion object {
        fun getUser(): User? {
            val userViewModel = UserViewModel(FirebaseAuth.getInstance())
            return userViewModel.user.value
        }
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val userData = Firebase.database.getReference("users").child(auth.uid.toString())

            userData.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tempUser: User? = snapshot.getValue<User>()
                    _user.postValue(tempUser!!)
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}