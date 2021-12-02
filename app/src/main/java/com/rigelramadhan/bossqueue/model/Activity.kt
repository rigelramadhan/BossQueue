package com.rigelramadhan.bossqueue.model

data class Activity(
    val id: Int,
    val userId: Int,
    val placeId: Int,
    val foodId: Int,
    val paid: Boolean,
)
