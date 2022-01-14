package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class Place(
    var id: String? = null,
    val name: String? = null,
    val location: String? = null,
    val description: String? = null,
    val open: Boolean? = null,
    val picture: String? = null
) : Parcelable {
    companion object {
        fun DocumentSnapshot.toPlace() : Place? {
            return try {
                val name = getString("name")
                val location = getString("location")
                val description = getString("description")
                val open = getBoolean("open")
                val picture = getString("picture")
                Place(id, name, location, description, open, picture)
            } catch(e: Exception) {
                null
            }
        }
    }
}
