package com.curidev.ayni.order.remote

import com.curidev.ayni.order.domain.model.Sale
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SaleService {
    @GET("sales/{saleId}")
    fun getSaleById(@Path("saleId") id: Int): Call<Sale>
}