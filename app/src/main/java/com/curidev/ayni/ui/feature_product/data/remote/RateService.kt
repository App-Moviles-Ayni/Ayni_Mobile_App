package com.curidev.ayni.feature_product.data.remote

import com.curidev.ayni.feature_product.domain.model.Rate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
interface RateService {
    @GET("products")
    fun getAll(): Call<List<RateResponse>>

    @GET("products/{productId}")
    fun getProductById(@Path("productId") id: Int): Call<Rate>
}