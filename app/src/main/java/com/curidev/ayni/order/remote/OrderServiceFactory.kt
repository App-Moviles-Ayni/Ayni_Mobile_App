package com.curidev.ayni.order.remote

import com.curidev.ayni.network.RetrofitFactory

class OrderServiceFactory private constructor() {
    companion object {
        fun getOrderService(): OrderService {
            return RetrofitFactory.getRetrofit().create(OrderService::class.java)
        }
    }
}