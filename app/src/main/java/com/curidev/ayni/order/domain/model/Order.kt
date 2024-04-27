package com.curidev.ayni.order.domain.model

data class Order(
    val id: Int,
    val quantity: Int,
    val status: String,
    val totalPrice: Double,
)
