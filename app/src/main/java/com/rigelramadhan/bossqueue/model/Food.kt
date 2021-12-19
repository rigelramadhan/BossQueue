package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
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
): Parcelable
