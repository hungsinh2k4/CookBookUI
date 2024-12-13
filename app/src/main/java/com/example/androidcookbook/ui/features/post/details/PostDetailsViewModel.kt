package com.example.androidcookbook.ui.features.post.details

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.PostRepository
import com.example.androidcookbook.domain.model.post.Comment
import com.example.androidcookbook.domain.model.post.SendCommentRequest
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.domain.usecase.DeletePostUseCase
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PostDetailsViewModel.PostDetailsViewModelFactory::class)
class PostDetailsViewModel @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted private val _postId: Int,
    @Assisted private val currentUser: User,
    private val postRepository: PostRepository,
    private val deletePostUseCase: DeletePostUseCase,
    private val makeToastUseCase: MakeToastUseCase,
) : ViewModel() {

    @AssistedFactory
    interface PostDetailsViewModelFactory {
        fun create(
            postId: Int,
            currentUser: User,
        ): PostDetailsViewModel
    }

    var isRefreshing = MutableStateFlow(false)
        private set
    var postUiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.Loading)
        private set
    var showBottomCommentSheet: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var commentsFlow: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
        private set
    var commentPage: MutableStateFlow<Int> = MutableStateFlow(1)
        private set
    var editCommentState: MutableStateFlow<EditCommentState> = MutableStateFlow(EditCommentState.NotEditing)
        private set
    var isPostLiked: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var isPostBookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var postLikes: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
        private set
    var isTogglingLike: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var isTogglingBookmark = false
    var showLikeCountDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        refresh()
    }

    fun refresh() {
        isRefreshing.update { true }
        viewModelScope.launch {
            //        postUiState.update { PostUiState.Success(post = _post) }
            queryPostLike(_postId)
            queryPostBookmark(_postId)
        }.invokeOnCompletion {
            isRefreshing.update { false }
        }
    }

    fun getPostLikes() {
        viewModelScope.launch {
            val response = postRepository.getPostLikes(_postId, 1)
            response.onSuccess {
                postLikes.update { data.likes }
            }.onFailure {
                postLikes.update { emptyList() }
            }
        }
    }

    fun updateShowBottomCommentSheet(value: Boolean) {
        showBottomCommentSheet.update { value }
    }

    suspend fun getPost() {
        val response = postRepository.getPost(_postId)
        response.onSuccess {
            postUiState.update { PostUiState.Success(post = data) }
        }.onFailure {
            postUiState.update { PostUiState.Error(message = message()) }
        }
        Log.d("PostDetails", response.toString())

    }

    private suspend fun queryPostLike(postId: Int) {
        val response = postRepository.queryPostLike(postId, currentUser.id)
        response.onSuccess {
            isPostLiked.update { data.isLiked }
        }.onFailure {
            isPostLiked.update { false }
            Log.d("PostDetails", message())
        }

    }

    private suspend fun queryPostBookmark(postId: Int) {
        val response = postRepository.queryPostBookmark(postId, currentUser.id)
        response.onSuccess {
            isPostBookmarked.update { data.isFavorited }
        }.onFailure {
            isPostBookmarked.update { false }
            Log.d("PostDetails", message())
        }

    }

    private fun likePost() {
        viewModelScope.launch {
            if (isTogglingLike.value) return@launch

            isTogglingLike.update { true }
            val response = postRepository.likePost(_postId)
            response.onSuccess {
                isPostLiked.update { true }
                getPostLikes()
                isTogglingLike.update { false }
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to like post")
                isTogglingLike.update { false }
            }
        }
    }

    private fun unlikePost() {
        viewModelScope.launch {
            if (isTogglingLike.value) return@launch
            isTogglingLike.update { true }
            val response = postRepository.unlikePost(_postId)
            response.onSuccess {
                isPostLiked.update { false }
                getPostLikes()
                isTogglingLike.update { false }
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to unlike post")
                isTogglingLike.update { false }
            }
        }
    }

    fun togglePostLike() {
        if (isPostLiked.value) {
            unlikePost()
        } else {
            likePost()
        }
    }

    private fun bookmarkPost() {
        viewModelScope.launch {
            if (isTogglingBookmark) return@launch
            isTogglingBookmark = true
            val response = postRepository.bookmarkPost(_postId)
            response.onSuccess {
                isPostBookmarked.update { true }
                isTogglingBookmark = false
            }.onFailure {
                showToast("Failed to bookmark post")
                isTogglingBookmark = false
            }
        }
    }

    private fun unBookmarkPost() {
        viewModelScope.launch {
            if (isTogglingBookmark) return@launch
            isTogglingBookmark = true
            val response = postRepository.unBookmarkPost(_postId)
            response.onSuccess {
                isPostBookmarked.update { false }
                isTogglingBookmark = false
            }.onFailure {
                showToast("Failed to unbookmark post")
                isTogglingBookmark = false
            }
        }
    }

    fun togglePostBookmark() {
        if (isPostBookmarked.value) {
            unBookmarkPost()
        } else {
            bookmarkPost()
        }
    }

    suspend fun getComments(reset: Boolean) {
        if (reset) {
            commentsFlow.update { emptyList() }
            commentPage.update { 1 }
        }
        val response = postRepository.getComments(_postId, currentUser.id, commentPage.value)
        response.onSuccess {
            commentsFlow.update { it + data.comments }
            commentPage.update { it + 1 }
            Log.d("PostDetails", data.toString())
        }.onFailure {
            Log.d("PostDetails", message())
            showToast("Failed to get comments")
        }

    }

    fun sendComment(content: String) {
        viewModelScope.launch {
            val response = postRepository.sendComment(
                postId = _postId,
                request = SendCommentRequest(content),
            )
            response.onSuccess {
                viewModelScope.launch {
                    getComments(true)
                }
                Log.d("PostDetails", data.toString())
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to send comment")
            }
        }
    }

    fun editComment(content: String) {
        viewModelScope.launch {
            val comment = (editCommentState.value as EditCommentState.Editing).comment
            val response = postRepository.editComment(
                commentId = comment.id, SendCommentRequest(content)
            )
            response.onSuccess {
                viewModelScope.launch {
                    getComments(true)
                }
                exitEditCommentState()
                Log.d("PostDetails", data.toString())
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to edit comment")
            }
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch {
            val response = postRepository.deleteComment(
                commentId = comment.id,
            )
            response.onSuccess {
                viewModelScope.launch {
                    getComments(true)
                }
                Log.d("PostDetails", data.toString())
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to delete comment")
            }
        }
    }

    fun toggleLikeComment(comment: Comment) {
        viewModelScope.launch {
            if (comment.isLiked) {
                unlikeComment(comment)
            } else {
                likeComment(comment)
            }
        }
    }

    private fun likeComment(comment: Comment) {
        viewModelScope.launch {
            val response = postRepository.likeComment(comment.id)
            response.onSuccess {
                // update the target comment isLiked field
                updateCommentIsLiked(comment, true)
                Log.d("PostDetails", data.toString())
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to like comment")
            }
        }
    }

    private fun unlikeComment(comment: Comment) {
        viewModelScope.launch {
            val response = postRepository.unlikeComment(comment.id)
            response.onSuccess {
                updateCommentIsLiked(comment, false)
                Log.d("PostDetails", data.toString())
            }.onFailure {
                Log.d("PostDetails", message())
                showToast("Failed to unlike comment")
            }
        }
    }

    private fun updateCommentIsLiked(comment: Comment, isLiked: Boolean) {
        commentsFlow.update { comments ->
            comments.map {
                if (it.id == comment.id) {
                    it.copy(
                        isLiked = isLiked,
                        totalLike = if (isLiked) it.totalLike + 1 else it.totalLike - 1,
                    )
                } else {
                    it
                }
            }
        }
    }

    fun enterEditCommentState(comment: Comment) {
        viewModelScope.launch {
            editCommentState.update { EditCommentState.Editing(comment) }
        }
    }

    fun exitEditCommentState() {
        viewModelScope.launch {
            editCommentState.update { EditCommentState.NotEditing }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            makeToastUseCase(message)
        }
    }

    fun deletePost(onSuccessNavigate: () -> Unit) {
        viewModelScope.launch {
            val response = deletePostUseCase(_postId)
            response.onSuccess {
                onSuccessNavigate()
            }.onFailure {
                viewModelScope.launch {
                    makeToastUseCase("Failed to delete post")
                }
            }
        }
    }

    fun updateShowLikeCountDialog(boolean: Boolean) {
        showLikeCountDialog.update { boolean }
    }
}


