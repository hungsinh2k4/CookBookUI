package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.post.Post
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsfeedService {
    @GET("newsfeed/{limit}")
    suspend fun getNewsfeed(@Path("limit") limit: Int): ApiResponse<List<Post>>
}