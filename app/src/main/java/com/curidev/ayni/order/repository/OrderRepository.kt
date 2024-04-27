package com.curidev.ayni.order.repository

import android.util.Log
import com.curidev.ayni.order.domain.model.Order
import com.curidev.ayni.order.remote.OrderResponse
import com.curidev.ayni.order.remote.OrderService
import com.curidev.ayni.order.remote.OrderServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(
    private val orderService: OrderService = OrderServiceFactory.getOrderService()
) {
    fun getAll(callback: (List<Order>) -> Unit) {
        val getAll = orderService.getAll()

        getAll.enqueue(object: Callback<List<OrderResponse>> {
            override fun onResponse(
                call: Call<List<OrderResponse>>,
                response: Response<List<OrderResponse>>) {
                    if (response.isSuccessful) {
                        val ordersResponse = response.body() as List<OrderResponse>
                        var orders: List<Order> = emptyList()
                        for (orderResponse in ordersResponse) {
                            orders = orders + Order(
                                orderResponse.id,
                                orderResponse.quantity,
                                orderResponse.status,
                                orderResponse.totalPrice
                            )
                        }
                        callback(orders)
                    }
                }

            override fun onFailure(call: Call<List<OrderResponse>>, t: Throwable) {
                t.message?.let {
                    Log.d("OrderRepository", it)
                }
            }
        })
    }

    fun getOrderById(id: Int, callback: (Order) -> Unit) {
        val getOrderById = orderService.getOrderById(id = id)

        getOrderById.enqueue(object: Callback<Order> {
            override fun onResponse(
                call: Call<Order>,
                response: Response<Order>) {
                    if (response.isSuccessful) {
                        callback(response.body() as Order)
                    }
                }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                t.message?.let {
                    Log.d("OrderRepository", it)
                }
            }
        })
    }
}