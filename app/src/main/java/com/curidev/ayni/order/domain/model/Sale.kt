package com.curidev.ayni.order.domain.model

data class Sale(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val userId: Int
)
