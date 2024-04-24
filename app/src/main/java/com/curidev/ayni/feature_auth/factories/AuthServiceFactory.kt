package com.curidev.ayni.feature_auth.factories

import com.curidev.ayni.factories.RetrofitFactory
import com.curidev.ayni.feature_auth.data.remote.AuthService

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