package com.curidev.ayni.feature_auth.factories

import com.curidev.ayni.network.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory private constructor() {

    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ApiClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit as Retrofit
        }
    }

}