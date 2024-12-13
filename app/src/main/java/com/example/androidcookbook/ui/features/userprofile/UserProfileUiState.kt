package com.example.androidcookbook.ui.features.userprofile

import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User

sealed interface UserProfileUiState {
    data object Guest: UserProfileUiState
    data object Loading : UserProfileUiState
    data class Failure(val message: String): UserProfileUiState
    data class Success(
        val user: User,
    ) : UserProfileUiState
}

sealed interface UserPostState {
    data object Guest: UserPostState
    data object Loading : UserPostState
    data object Failure: UserPostState
    data class Success(
        val userPosts: List<Post>,
    ) : UserPostState
}
