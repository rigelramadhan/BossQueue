package com.rigelramadhan.bossqueue.model

data class Place(
    val id: Int,
    val name: String,
    val location: String,
    val description: String,
    val open: Boolean,
    val picture: String
)
