package com.curidev.ayni.feature_auth.data.repository

import android.util.Log
import com.curidev.ayni.feature_auth.data.remote.UserService
import com.curidev.ayni.feature_auth.data.remote.UserServiceFactory
import com.curidev.ayni.feature_auth.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val userService: UserService = UserServiceFactory.getUserService()
) {
    fun getUserById(id: Int, callback: (User) -> Unit) {
        val getUserById = userService.getUserById(id=id)

        getUserById.enqueue(object: Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                    if (response.isSuccessful) {
                        callback(response.body() as User)
                    }
                }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message?.let {
                    Log.d("UserRepository", it)
                }
            }
        })
    }
}