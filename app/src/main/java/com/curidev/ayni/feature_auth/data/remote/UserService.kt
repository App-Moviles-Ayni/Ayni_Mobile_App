package com.curidev.ayni.feature_auth.data.remote

import com.curidev.ayni.feature_auth.domain.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{userId}")
    fun getUserById(@Path("userId") id: Int): Call<User>
}