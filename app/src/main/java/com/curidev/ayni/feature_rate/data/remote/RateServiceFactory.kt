package com.curidev.ayni.feature_product.data.remote

import com.curidev.ayni.feature_rate.data.remote.RateService
import com.curidev.ayni.network.RetrofitFactory

class RateServiceFactory private constructor() {
    companion object {
        fun getRateService(): RateService {
            return RetrofitFactory.getRetrofit().create(RateService::class.java)
        }
    }
}