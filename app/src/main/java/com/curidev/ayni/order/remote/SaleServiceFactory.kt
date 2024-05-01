package com.curidev.ayni.order.remote

import com.curidev.ayni.network.RetrofitFactory

class SaleServiceFactory private constructor() {
    companion object {
        fun getSaleService(): SaleService {
            return RetrofitFactory.getRetrofit().create(SaleService::class.java)
        }
    }
}