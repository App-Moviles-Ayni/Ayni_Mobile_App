package com.curidev.ayni.order.remote

data class OrderResponse(
    val id: Int,
    val description: String,
    val totalPrice: Double,
    val quantity: Int,
    val paymentMethod: String,
    val saleId: Int,
    val orderedBy: Int,
    val acceptedBy: Int,
    val orderedDate: String,
    val status: String
)