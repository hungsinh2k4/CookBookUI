package com.example.androidcookbook.network

import com.example.androidcookbook.model.api.ApiResponse
import com.example.androidcookbook.model.api.SignInRequest
import com.example.androidcookbook.model.signup.RegisterRequest
import com.example.androidcookbook.model.signup.RegisterResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/auth/login")
    fun signin(@Body request: SignInRequest): Call<ApiResponse>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://cookbookbe.onrender.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val authApi: AuthApi by lazy {
    retrofit.create(AuthApi::class.java)
}