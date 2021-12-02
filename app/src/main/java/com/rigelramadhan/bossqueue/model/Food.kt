package com.rigelramadhan.bossqueue.model

data class Food(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val picture: String,
    val categoryId: Int,
    val placeId: Int
)
