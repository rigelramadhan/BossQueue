package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    var id: String? = null,
    val name: String? = null,
    val location: String? = null,
    val description: String? = null,
    val open: Boolean? = null,
    val picture: String? = null
) : Parcelable
