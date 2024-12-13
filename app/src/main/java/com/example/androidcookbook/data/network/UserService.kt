package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.follow.FollowersResponse
import com.example.androidcookbook.domain.model.follow.FollowingResponse
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.domain.network.SuccessMessageBody
import com.example.androidcookbook.domain.network.UpdateUserRequest
import com.example.androidcookbook.domain.network.UserFavoritesResponse
import com.example.androidcookbook.domain.network.UserLikesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("profile/{userId}")
    suspend fun getUserProfile(@Path("userId") userId: Int): ApiResponse<User>

    @GET("profile/posts/{userId}")
    suspend fun getUserPosts(@Path("userId") userId: Int): ApiResponse<List<Post>>

    @PUT("profile/edit")
    suspend fun updateUser(@Body request: UpdateUserRequest): ApiResponse<SuccessMessageBody>

    @GET("follows/followers/{userId}")
    suspend fun getUserFollowers(@Path("userId") userId: Int): ApiResponse<FollowersResponse>

    @GET("follows/following/{userId}")
    suspend fun getUserFollowing(@Path("userId") userId: Int): ApiResponse<FollowingResponse>

    @POST("follows/{userId}")
    suspend fun followUser(@Path("userId") userId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("follows/{userId}")
    suspend fun unfollowUser(@Path("userId") userId: Int): ApiResponse<SuccessMessageBody>

    @GET("favorite/{page}")
    suspend fun getUserFavoritePosts(@Path("page") page: Int): ApiResponse<UserFavoritesResponse>

    @GET("like/{page}")
    suspend fun getUserLikedPosts(@Path("page") page: Int): ApiResponse<UserLikesResponse>

}
