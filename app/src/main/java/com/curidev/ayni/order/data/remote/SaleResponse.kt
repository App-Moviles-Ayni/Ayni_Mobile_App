package com.curidev.ayni.order.data.remote

data class SaleResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val userId: Int
)
