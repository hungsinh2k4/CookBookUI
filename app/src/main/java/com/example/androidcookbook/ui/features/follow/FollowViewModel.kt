package com.example.androidcookbook.ui.features.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.UserRepository
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = FollowViewModel.FollowViewModelFactory::class)
class FollowViewModel @AssistedInject constructor(
    @Assisted("currentUser") private val currentUser: User,
    @Assisted("targetUserId") private val targetUserId: Int,
    private val userRepository: UserRepository,
    private val makeToastUseCase: MakeToastUseCase,

) : ViewModel() {
    var isRefreshing = MutableStateFlow(false)
        private set
    private val _isFollowing = MutableStateFlow(false)
    val isFollowing: StateFlow<Boolean> = _isFollowing

    private val _followers = MutableStateFlow<List<User>>(emptyList())
    val followers: StateFlow<List<User>> = _followers

    private val _following = MutableStateFlow<List<User>>(emptyList())
    val following: StateFlow<List<User>> = _following

    var currentUserFollowing = MutableStateFlow<List<User>>(emptyList())

    @AssistedFactory
    interface FollowViewModelFactory {
        fun create(
            @Assisted("currentUser") currentUser: User,
            @Assisted("targetUserId") targetUserId: Int,
        ): FollowViewModel
    }

    init {
        refresh()
    }

    fun refresh() {
        isRefreshing.update { true }
        viewModelScope.launch {
            getCurrentUserFollowing()
            getFollowers()
            getFollowing()
        }.invokeOnCompletion {
            isRefreshing.update { false }
        }
    }

    fun toggleFollow(user: User) {
        if (_isFollowing.value) {
            unfollowUser(user)
        } else {
            followUser(user)
        }
        refresh()
    }

    fun unfollowUser(user: User) {
        viewModelScope.launch {
            userRepository.unfollowUser(user.id)
                .onSuccess {
                    _isFollowing.update { false }
//                    currentUserFollowing.update { it.toMutableList().apply { remove(user) }}
//                    _followers.update { it.toMutableList().apply { remove(user) }}
                    refresh()
                }
                .onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to unfollow user")
                    }
                }
        }
    }

    fun followUser(user: User) {
        viewModelScope.launch {
            val response = userRepository.followUser(user.id)
            response.onSuccess {
                    _isFollowing.update { true }
//                    currentUserFollowing.update { it.toMutableList().apply { add(user) }}
//                    _followers.update { it.toMutableList().apply { add(user) }}
                    refresh()
                }
                .onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to follow user")
                    }
                }
        }
    }

    private fun getFollowers() {
        viewModelScope.launch {
            val response = userRepository.getUserFollowers(targetUserId)
            response.onSuccess {
                    _followers.update { data.followers }
                    _isFollowing.update { checkFollowing() }
                }
                .onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to get followers")
                    }
                }
        }
    }

    private fun getFollowing() {
        viewModelScope.launch {
            userRepository.getUserFollowing(targetUserId)
                .onSuccess {
                    _following.update { data.following }
                }
                .onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to get following")
                    }
                }
        }
    }

    private fun getCurrentUserFollowing() {
        viewModelScope.launch {
            userRepository.getUserFollowing(currentUser.id)
                .onSuccess {
                    currentUserFollowing.update { data.following }
                }
                .onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to get current user following, incorrect info might be displayed")
                    }
                }
        }
    }

    private fun checkFollowing(): Boolean {
        return followers.value.find { it.id == currentUser.id } != null
    }

    fun isCurrentUserFollowing(user: User): Boolean {
        return currentUserFollowing.value.find { it.id == user.id } != null
    }
}