package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val id: String,
    val name: String,
    val location: String,
    val description: String,
    val open: Boolean,
    val picture: String
) : Parcelable
