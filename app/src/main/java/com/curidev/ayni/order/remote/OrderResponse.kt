package com.curidev.ayni.order.remote

data class OrderResponse(
    val id: Int,
    val quantity: Int,
    val status: String,
    val totalPrice: Double
)