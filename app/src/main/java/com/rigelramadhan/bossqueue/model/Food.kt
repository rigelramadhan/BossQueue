package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val picture: String,
    val categoryId: Int,
    val placeId: String
): Parcelable
