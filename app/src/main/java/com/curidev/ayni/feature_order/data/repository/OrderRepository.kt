package com.curidev.ayni.feature_order.data.repository

import android.util.Log
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.feature_order.data.remote.OrderResponse
import com.curidev.ayni.feature_order.data.remote.OrderService
import com.curidev.ayni.feature_order.data.remote.OrderServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class   OrderRepository(
    private val orderService: OrderService = OrderServiceFactory.getOrderService()
) {
    fun createOrder(order: Order, callback: (Order) -> Unit) {
        val createOrder = orderService.createOrder(order)

        createOrder.enqueue(object: Callback<Order> {
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

    fun qualifyOrder(id: Int, callback: (Order) -> Unit) {
        val qualifyOrder = orderService.qualifyOrder(id)

        qualifyOrder.enqueue(object: Callback<Order> {
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
                                orderResponse.description,
                                orderResponse.totalPrice,
                                orderResponse.quantity,
                                orderResponse.paymentMethod,
                                orderResponse.saleId,
                                orderResponse.orderedBy,
                                orderResponse.acceptedBy,
                                orderResponse.orderedDate,
                                orderResponse.status
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

    fun deleteOrderById(id: Int, callback: (Boolean) -> Unit) {
        val deleteOrderById = orderService.deleteOrderById(id = id)

        deleteOrderById.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    Log.d("OrderRepository", "Failed to delete order. Error: ${response.message()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.d("OrderRepository", "Failed to delete order. Error: ${t.message}")
                callback(false)
            }
        })
    }
}