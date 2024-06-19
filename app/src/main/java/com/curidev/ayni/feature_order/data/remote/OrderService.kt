package com.curidev.ayni.feature_order.data.remote

import com.curidev.ayni.feature_order.domain.model.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @POST("orders")
    fun createOrder(@Body order: Order): Call<Order>

    @POST("orders/{orderId}/qualifications")
    fun qualifyOrder(@Path("orderId") id: Int): Call<Order>

    @GET("orders")
    fun getAll(): Call<List<OrderResponse>>

    @GET("orders/{orderId}")
    fun getOrderById(@Path("orderId") id: Int): Call<Order>

    @DELETE("orders/{orderId}")
    fun deleteOrderById(@Path("orderId") id: Int): Call<Order>
}