package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val location: String? = null
): Parcelable
