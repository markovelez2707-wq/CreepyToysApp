package com.example.creepytoys.domain.model

import androidx.annotation.DrawableRes
data class ToyItem(
    val id: String,
    val name: String,
    val franchise: String,
    val price: Double,
    val stock: Int,
    val minAge: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)

