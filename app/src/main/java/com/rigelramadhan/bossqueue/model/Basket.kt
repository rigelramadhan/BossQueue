package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class Basket(
    val basketId: String? = null,
    var userId: String? = null,
    val placeId: String? = null,
    val foodId: String? = null
) : Parcelable {
    companion object {
        fun DocumentSnapshot.toBasket() : Basket? {
            return try {
                val userId = getString("userId")
                val placeId = getString("placeId")
                val foodId = getString("foodId")
                Basket(id, userId, placeId, foodId)
            } catch (e: Exception) {
                null
            }
        }
    }
}