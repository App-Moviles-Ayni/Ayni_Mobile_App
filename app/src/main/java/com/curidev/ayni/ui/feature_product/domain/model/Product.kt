package com.curidev.ayni.feature_product.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val recommendedCultivationDistance: String,
    val recommendedCultivationDepth: String,
    val recommendedGrowingClimate: String,
    val recommendedSoilType: String,
    val recommendedGrowingSeason: String,
    var imageUrl: String,
    val userId: Int,
)
