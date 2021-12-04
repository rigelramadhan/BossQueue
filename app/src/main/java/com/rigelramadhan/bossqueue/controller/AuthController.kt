package com.rigelramadhan.bossqueue.controller

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.User
import com.rigelramadhan.bossqueue.view.MainNavActivity
import com.rigelramadhan.bossqueue.view.ui.auth.LoginActivity
import com.rigelramadhan.bossqueue.view.ui.auth.RegisterActivity

class AuthController(private val activity: AppCompatActivity) {
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference

    companion object {
        const val TAG = "AuthController"
    }

    public fun checkIfUserSignedIn() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(activity, LoginActivity::class.java)
            directToLogin()
        }
    }

    public fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    directToMainMenu()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    public fun signUpWithEmailAndPassword(name: String, email: String, password: String, location: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        createUser(user.uid, name, email, password, location)
                    }
                    directToLogin()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    public fun signOut() {
        auth.signOut()
    }

    fun createUser(userId: String, name: String, email: String, password: String, location: String) {
        val user = User(name, email, password, location)

        database.child("users").child(userId).setValue(user)
    }

    fun getUser(): User {
        val userData = Firebase.database.getReference("users").child(auth.uid.toString())
        var user = User()

        userData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempUser: User? = snapshot.getValue<User>()
                if (tempUser != null) {
                    user = tempUser
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        return user
    }

    private fun directToMainMenu() {
        val intent = Intent(activity, MainNavActivity::class.java)
        activity.startActivity(intent)
    }

    private fun directToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    public fun directToRegister() {
        val intent = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(intent)
    }
}