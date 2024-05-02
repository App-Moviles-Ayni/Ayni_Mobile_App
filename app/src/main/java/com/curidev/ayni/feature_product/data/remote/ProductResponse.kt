package com.curidev.ayni.feature_product.data.remote

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val recommendedCultivationDistance: String,
    val recommendedCultivationDepth: String,
    val recommendedGrowingClimate: String,
    val recommendedSoilType: String,
    val recommendedGrowingSeason: String,
    var imageUrl: String,
    val userId: Int
)
