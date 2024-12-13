package com.example.androidcookbook.ui.features.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.UserRepository
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.usecase.DeletePostUseCase
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = UserProfileViewModel.UserProfileViewModelFactory::class)
class UserProfileViewModel @AssistedInject constructor(
    @Assisted private val userId: Int,
    private val userRepository: UserRepository,
    private val deletePostUseCase: DeletePostUseCase,
    private val makeToastUseCase: MakeToastUseCase,
) : ViewModel() {

    @AssistedFactory
    interface UserProfileViewModelFactory {
        fun create(user: Int): UserProfileViewModel
    }

    var isRefreshing = MutableStateFlow(false)
        private set

    var uiState: MutableStateFlow<UserProfileUiState> = MutableStateFlow(
        if (userId == GUEST_ID) UserProfileUiState.Guest
        else UserProfileUiState.Loading
    )
        private set

    var userPostState: MutableStateFlow<UserPostState> = MutableStateFlow(
        if (userId == GUEST_ID) UserPostState.Guest
        else UserPostState.Loading
    )

    var userPostPortionType: MutableStateFlow<UserPostPortionType> = MutableStateFlow(UserPostPortionType.Posts)
        private set


    init {
        viewModelScope.launch {
            getUser(userId = userId)
        }
    }

    fun setUserPostPortionType(type: UserPostPortionType) {
        userPostPortionType.update { type }
    }

    private suspend fun getUser(userId: Int) {
        if (userId == GUEST_ID) {
            uiState.update { UserProfileUiState.Guest }
            return
        }
        uiState.update { UserProfileUiState.Loading }
        userRepository.getUserProfile(userId = userId)
            .onSuccess {
                uiState.update { UserProfileUiState.Success(user = data) }
                when(userPostPortionType.value){
                    UserPostPortionType.Posts -> getUserPosts(userId)
                    UserPostPortionType.Favorites -> getUserFavoritePosts()
                    UserPostPortionType.Likes -> {}
                }
            }
            .onFailure {
                uiState.update { UserProfileUiState.Failure(message()) }
            }
    }

    fun getUserPosts(userId: Int) {
        if (userId == GUEST_ID) {
            userPostState.update { UserPostState.Guest }
            return
        }
        userPostState.update { UserPostState.Loading }

        viewModelScope.launch {
            userRepository.getUserPosts(userId)
                .onSuccess {
                    userPostState.update { UserPostState.Success(data) }
                }
                .onFailure { userPostState.update { UserPostState.Failure } }
//            userRepository.getUserLikedPosts(userId).onSuccess {
//                userLikedPosts = data.likes
//            }.onFailure {
//                userPostState.update { UserPostState.Failure }
//            }
        }
    }

    fun getUserFavoritePosts() {
        userPostState.update { UserPostState.Loading }
        viewModelScope.launch {
            userRepository.getUserFavoritePosts(1).onSuccess {
                userPostState.update { UserPostState.Success(data.favorites) }
            }.onFailure {
                userPostState.update { UserPostState.Failure }
            }
        }
    }

    fun refresh() {
        if (userId == GUEST_ID) return
        isRefreshing.update { true }
        viewModelScope.launch {
            getUser(userId = userId)
        }.invokeOnCompletion {
            isRefreshing.update { false }
        }
    }

    fun refreshNoIndicator() {
        if (userId == GUEST_ID) return
        viewModelScope.launch {
            getUser(userId = userId)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            deletePostUseCase(post.id).onSuccess {
                refresh()
            }.onFailure {
                viewModelScope.launch {
                    makeToastUseCase("Failed to delete post")
                }
            }
        }
    }
}