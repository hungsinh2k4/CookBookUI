package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.NewsfeedService
import javax.inject.Inject

class NewsfeedRepository @Inject constructor(
    private val newsfeedService: NewsfeedService
) {
    suspend fun getNewsfeed(limit: Int) = newsfeedService.getNewsfeed(limit)
}