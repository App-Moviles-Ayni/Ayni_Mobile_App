package com.curidev.ayni.feature_order.data.remote

import com.curidev.ayni.network.RetrofitFactory

class OrderServiceFactory private constructor() {
    companion object {
        fun getOrderService(): OrderService {
            return RetrofitFactory.getRetrofit().create(OrderService::class.java)
        }
    }
}