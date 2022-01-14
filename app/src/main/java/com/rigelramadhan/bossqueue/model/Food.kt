package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    var id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val picture: String? = null,
    val categoryId: Int? = null,
    val placeId: String? = null
): Parcelable {
    companion object {
        fun DocumentSnapshot.toFood() : Food? {
            return try {
                val name = getString("name")
                val description = getString("description")
                val price = getDouble("price")
                val picture = getString("picture")
                val categoryId = getLong("categoryId")?.toInt()
                val placeId = getString("placeId")
                Food(id, name, description, price, picture, categoryId, placeId)
            } catch (e: Exception) {
                null
            }
        }
    }
}
