package com.example.androidcookbook.domain.model.search

import com.example.androidcookbook.data.mocks.SamplePosts
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User

data class SearchAll(
    val posts: List<Post> = SamplePosts.posts,
    val users: List<User> = listOf(User())
)

data class SearchPost(
    val nextPage: Boolean = true,
    val posts: List<Post> = SamplePosts.posts
)

data class SearchUser(
    val nextPage: Boolean = true,
    val users: List<User> = listOf()
)