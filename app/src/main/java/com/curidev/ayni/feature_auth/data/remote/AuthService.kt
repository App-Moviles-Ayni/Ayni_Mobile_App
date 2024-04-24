package com.curidev.ayni.feature_auth.data.remote

import com.curidev.ayni.feature_auth.domain.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("users")
    fun signUp(@Body request: UserRequest): Call<User>

}