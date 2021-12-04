package com.rigelramadhan.bossqueue.model

data class Category (
    val id: Int,
    val name: String,
    val pictures: String
) {
    companion object {
        const val FOOD = 1
        const val DRINK = 2
    }
}

