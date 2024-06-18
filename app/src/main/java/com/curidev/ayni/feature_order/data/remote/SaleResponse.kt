package com.curidev.ayni.feature_order.data.remote

data class SaleResponse(
    val id: Int,
    val name: String,
    val description: String,
    val unitPrice: Double,
    val quantity: Int,
    val imageUrl: String,
    val userId: Int
)
