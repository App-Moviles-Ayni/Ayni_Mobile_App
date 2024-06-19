package com.curidev.ayni.feature_rate.data.remote

import com.curidev.ayni.feature_product.data.remote.RateResponse
import com.curidev.ayni.feature_product.domain.model.Rate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RateService {

    @POST("rates")
    fun createRate(@Body rate: Rate): Call<Rate>

    @GET("rates")
    fun getAll(): Call<List<RateResponse>>

    @GET("rates/{rateId}")
    fun getRateById(@Path("rateId") id: Int): Call<RateResponse>
}