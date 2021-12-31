package com.rigelramadhan.bossqueue.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Basket(
    var userId: String? = null,
    val placeId: String? = null,
    val foodId: String? = null
) : Parcelable