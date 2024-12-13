package com.example.androidcookbook.ui.features.search

import androidx.compose.foundation.pager.PagerState
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.example.androidcookbook.data.mocks.SamplePosts
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.recipe.Recipe
import com.example.androidcookbook.domain.model.search.SearchAll
import com.example.androidcookbook.domain.model.user.User

enum class SearchScreenState {
    Food,
    Posts,
    Detail,
    Waiting
}

enum class SearchTab {
    All,
    Posts,
    Food,
    Users
}

enum class TabState {
    Idle,
    Waiting,
    Succeed,
    Failed
}

data class SearchTabUiState<T>(
    val nextPage: Boolean = false,
    val currentPage: Int = 0,
    val state: TabState = TabState.Idle,
    val messageStr: String = "",
    val result: List<T> = listOf(),
)

data class SearchUiState(
    val searchQuery: String = "",
    val result: String = "",
    val resultList: List<Recipe> = listOf(),
    val fail: Boolean = false,
    val currentScreen: SearchScreenState = SearchScreenState.Waiting,
    val currentPost: Post = SamplePosts.posts[0],
    val searchALlResults: SearchAll = SearchAll(),
    val userTabState: SearchTabUiState<User> = SearchTabUiState(),
    val postTabState: SearchTabUiState<Post> = SearchTabUiState(),
    val foodTabState: SearchTabUiState<Recipe> = SearchTabUiState()
)