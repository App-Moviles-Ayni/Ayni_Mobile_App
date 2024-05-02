package com.curidev.ayni.feature_product.data.remote

import com.curidev.ayni.feature_product.domain.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    fun getAll(): Call<List<ProductResponse>>

    @GET("products/{productId}")
    fun getProductById(@Path("productId") id: Int): Call<Product>
}