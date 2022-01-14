package com.rigelramadhan.bossqueue.firebaseservices

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rigelramadhan.bossqueue.model.User
import com.rigelramadhan.bossqueue.model.User.Companion.toUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FirebaseUserServices {
    private const val TAG = "FirebaseUserServices"
    suspend fun getUserData(userId: String): User? {
        val db = Firebase.firestore
        return try {
            db.collection("users")
                .document(userId)
                .get()
                .await()
                .toUser()
        } catch (e: Exception) {
            null
        }
    }
}