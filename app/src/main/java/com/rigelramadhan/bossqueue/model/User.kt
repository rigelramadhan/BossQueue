package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val location: String? = null
): Parcelable {
    companion object {
        fun DocumentSnapshot.toUser(): User? {
            return try {
                val name = getString("name")
                val email = getString("email")
                val location = getString("location")
                User(id, name, email, location)
            } catch (e: Exception) {
                null
            }
        }
    }
}
