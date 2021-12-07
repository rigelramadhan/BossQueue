package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val location: String
): Parcelable
