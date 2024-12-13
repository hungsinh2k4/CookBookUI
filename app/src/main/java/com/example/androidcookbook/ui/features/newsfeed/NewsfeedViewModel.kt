package com.example.androidcookbook.ui.features.newsfeed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.NewsfeedRepository
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.network.ErrorBody
import com.example.androidcookbook.domain.usecase.DeletePostUseCase
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.example.androidcookbook.ui.common.state.ScreenUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.serialization.onErrorDeserialize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsfeedViewModel @Inject constructor(
    private val newsfeedRepository: NewsfeedRepository,
    private val makeToastUseCase: MakeToastUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    var screenUiState = MutableStateFlow<ScreenUiState<List<Post>>>(ScreenUiState.Loading)
        private set
    var isRefreshing = MutableStateFlow(false)
        private set
    var posts = MutableStateFlow<List<Post>>(emptyList())
        private set

    private val newsfeedLimit = 5
    private var newsfeedOffset = newsfeedLimit
    val isLoadingMore: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        refresh()
    }

    private suspend fun getNewsfeed() {
        val response = newsfeedRepository.getNewsfeed(newsfeedOffset)
        response.onSuccess {
            posts.update { data }
            screenUiState.update { ScreenUiState.Success(data) }
        }.onErrorDeserialize<List<Post>, ErrorBody> { errorBody ->
            screenUiState.update { ScreenUiState.Failure("Something went wrong") }
        }.onException {
            screenUiState.update { ScreenUiState.Failure("Something went wrong") }
        }

    }

    fun refresh() {
        screenUiState.update { ScreenUiState.Loading }
        isRefreshing.update { true }
        viewModelScope.launch {
            getNewsfeed()
        }.invokeOnCompletion {
            isRefreshing.update { false }
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

    fun loadMore() {
        Log.d("NewsfeedViewModel", "loadMore")
        isLoadingMore.update { true }
        viewModelScope.launch {
//            newsfeedOffset += newsfeedLimit
            val response = newsfeedRepository.getNewsfeed(newsfeedLimit)
            response.onSuccess {
                val currentPost = posts.value.toMutableList()
                currentPost.addAll(data)
                posts.update {  currentPost }
                isLoadingMore.update { false }
            }.onFailure {
                viewModelScope.launch {
                    makeToastUseCase("Something went wrong while fetching more posts")
                }
                isLoadingMore.update { false }
            }
        }
    }
}