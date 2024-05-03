package com.curidev.ayni.feature_product.data.remote

data class RateResponse(
    val id: Int,
    val rate: Int,
    val date: String,
    val productId: Int,
    val userId: Int,
)
