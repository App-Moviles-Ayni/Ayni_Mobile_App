package com.curidev.ayni.feature_order.data.remote

import com.curidev.ayni.feature_order.domain.model.Sale
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SaleService {
    @GET("sales")
    fun getAll(): Call<List<SaleResponse>>
    @GET("sales/{saleId}")
    fun getSaleById(@Path("saleId") saleId: Int): Call<Sale>
}