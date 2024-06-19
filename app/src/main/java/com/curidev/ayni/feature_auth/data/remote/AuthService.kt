package com.curidev.ayni.feature_auth.data.remote

import com.curidev.ayni.feature_auth.domain.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("auth/signup")
    fun signUp(@Body request: UserRequest): Call<User>

    @POST("auth/signin")
    fun signIn(@Body request: SignInRequest): Call<User>

}