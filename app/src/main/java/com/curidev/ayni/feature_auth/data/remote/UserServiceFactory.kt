package com.curidev.ayni.feature_auth.data.remote

import com.curidev.ayni.network.RetrofitFactory

class UserServiceFactory private constructor() {
    companion object {
        fun getUserService(): UserService {
            return RetrofitFactory.getRetrofit().create(UserService::class.java)
        }
    }
}