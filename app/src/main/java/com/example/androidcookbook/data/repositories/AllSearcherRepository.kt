package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.AllSearcherService
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.search.SearchAll
import com.example.androidcookbook.domain.model.search.SearchPost
import com.example.androidcookbook.domain.model.search.SearchUser
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class AllSearcherRepository @Inject constructor(
    private val allSearcherService: AllSearcherService
) {
    suspend fun searchAll(query: String): ApiResponse<SearchAll> {
        return allSearcherService.searchAll(query)
    }

    suspend fun searchPosts(post: String, page: Int): ApiResponse<SearchPost> {
        return allSearcherService.searchPosts(post, page)
    }

    suspend fun searchUsers(name: String, page: Int): ApiResponse<SearchUser> {
        return allSearcherService.searchUsers(name, page)
    }

    suspend fun searchUsersByUsername(username: String, page: Int): ApiResponse<SearchUser> {
        return allSearcherService.searchUsersByUsername(username, page)
    }
}