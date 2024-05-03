package com.curidev.ayni.feature_product.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RateService {
    @GET("rates")
    fun getAll(): Call<List<RateResponse>>

    @GET("rates/{rateId}")
    fun getRateById(@Path("rateId") id: Int): Call<RateResponse>
}