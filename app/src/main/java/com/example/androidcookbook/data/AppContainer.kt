package com.example.androidcookbook.data


import com.example.androidcookbook.network.CookbookApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val cookbookRepository: CookbookRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: CookbookApiService by lazy {
        retrofit.create(CookbookApiService::class.java)
    }

    override val cookbookRepository: CookbookRepository by lazy {
        DefaultCookbookRepository(retrofitService)
    }

}