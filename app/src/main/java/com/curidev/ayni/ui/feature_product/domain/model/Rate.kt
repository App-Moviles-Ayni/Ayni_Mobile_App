package com.curidev.ayni.feature_product.domain.model

data class Rate(
    val id: Int,
    val rate: Int,
    val date: String,
    val productId: Int,
    val userId: Int,
)
