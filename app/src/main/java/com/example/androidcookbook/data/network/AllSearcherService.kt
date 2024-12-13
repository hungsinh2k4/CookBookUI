package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.search.SearchAll
import com.example.androidcookbook.domain.model.search.SearchPost
import com.example.androidcookbook.domain.model.search.SearchUser
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AllSearcherService {
    @GET("search/all/{query}")
    suspend fun searchAll(@Path("query") query: String): ApiResponse<SearchAll>

    @GET("search/post/{post}/{page}")
    suspend fun searchPosts(@Path("post") post: String, @Path("page") page: Int): ApiResponse<SearchPost>

    @GET("search/name/{name}/{page}")
    suspend fun searchUsers(@Path("name") name: String, @Path("page") page: Int): ApiResponse<SearchUser>

    @GET("search/username/{username}/{page}")
    suspend fun searchUsersByUsername(@Path("username") username: String, @Path("page") page: Int): ApiResponse<SearchUser>
}