package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.UserService
import com.example.androidcookbook.domain.network.UpdateUserRequest
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUserProfile(userId: Int) = userService.getUserProfile(userId)

    suspend fun getUserPosts(userId: Int) = userService.getUserPosts(userId)

    suspend fun updateUser(request: UpdateUserRequest) = userService.updateUser(request)

    suspend fun getUserFollowers(userId: Int) = userService.getUserFollowers(userId)

    suspend fun getUserFollowing(userId: Int) = userService.getUserFollowing(userId)

    suspend fun followUser(userId: Int) = userService.followUser(userId)

    suspend fun unfollowUser(userId: Int) = userService.unfollowUser(userId)

    suspend fun getUserLikedPosts(userId: Int) = userService.getUserLikedPosts(userId)

    suspend fun getUserFavoritePosts(page: Int) = userService.getUserFavoritePosts(page)

}
