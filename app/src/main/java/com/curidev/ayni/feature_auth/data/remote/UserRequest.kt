package com.curidev.ayni.feature_auth.data.remote

class UserRequest(
    val username: String,
    val email: String,
    val role: String,
    val password: String,
)