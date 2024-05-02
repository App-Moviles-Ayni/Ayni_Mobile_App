package com.curidev.ayni.order.remote

import com.curidev.ayni.order.domain.model.Order
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderService {
    @GET("orders")
    fun getAll(): Call<List<OrderResponse>>

    @GET("orders/{orderId}")
    fun getOrderById(@Path("orderId") id: Int): Call<Order>
}