package com.curidev.ayni.feature_auth.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.curidev.ayni.MyApplication
import com.curidev.ayni.feature_auth.data.remote.AuthService
import com.curidev.ayni.feature_auth.data.remote.SignInRequest
import com.curidev.ayni.feature_auth.data.remote.UserRequest
import com.curidev.ayni.feature_auth.domain.model.User
import com.curidev.ayni.feature_auth.factories.AuthServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val authService: AuthService = AuthServiceFactory.getAuthService()
) {
    private val context = MyApplication.getContext()

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("my_app_pref", Context.MODE_PRIVATE)
    }

    fun signUp(userRequest: UserRequest, callback: (User) -> Unit) {
        val signUp = authService.signUp(userRequest)
        signUp.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body() as User
                    callback(user)
                } else {
                    // Handle signUp failure here if needed
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }
            }
        })
    }

    fun signIn(username: String, password: String, callback: (User?) -> Unit) {
        val signInRequest = SignInRequest(username, password)
        val signIn = authService.signIn(signInRequest)
        signIn.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        saveUserId(it.id)
                    }
                    callback(user)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }
                callback(null)
            }
        })
    }

    private fun saveUserId(userId: String) {
        with(sharedPreferences.edit()) {
            putString("user_id", userId)
            apply()
        }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("user_id", null)
    }

    fun clearUserId() {
        sharedPreferences.edit().remove("user_id").apply()
    }
}
