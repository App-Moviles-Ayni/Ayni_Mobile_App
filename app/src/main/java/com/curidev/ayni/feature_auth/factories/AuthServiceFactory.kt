package com.curidev.ayni.feature_auth.factories

import com.curidev.ayni.feature_auth.data.remote.AuthService
import com.curidev.ayni.network.RetrofitFactory


class AuthServiceFactory private constructor() {

    companion object {
        private var authService: AuthService? = null

        fun getAuthService(): AuthService {
            if (authService == null) {
                authService = RetrofitFactory.getRetrofit().create(AuthService::class.java)
            }
            return authService as AuthService
        }
    }
}