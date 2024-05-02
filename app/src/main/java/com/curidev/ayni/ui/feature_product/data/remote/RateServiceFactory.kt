package com.curidev.ayni.feature_product.data.remote

import com.curidev.ayni.core_network.RetrofitFactory

class RateServiceFactory private constructor() {
    companion object {
        fun getRateService(): RateService {
            return RetrofitFactory.getRetrofit().create(RateService::class.java)
        }
    }
}