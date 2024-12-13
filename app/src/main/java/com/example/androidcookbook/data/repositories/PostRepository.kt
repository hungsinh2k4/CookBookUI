package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.PostService
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.post.PostCreateRequest
import com.example.androidcookbook.domain.model.post.SendCommentRequest
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {
    suspend fun createPost(post: PostCreateRequest) = postService.createPost(post)

    suspend fun deletePost(postId: Int) = postService.deletePost(postId)

    suspend fun updatePost(postId: Int, post: PostCreateRequest) = postService.updatePost(postId, post)

    suspend fun getPost(postId: Int) = postService.getPost(postId)

    suspend fun queryPostLike(postId: Int, userId: Int) = postService.queryPostLike(postId, userId)

    suspend fun likePost(id: Int) = postService.likePost(id)

    suspend fun unlikePost(id: Int) = postService.unlikePost(id)

    suspend fun getComments(postId: Int, userId: Int, page: Int) = postService.getComments(postId, userId, page)

    suspend fun sendComment(postId: Int, request: SendCommentRequest) = postService.sendComment(postId, request)

    suspend fun editComment(commentId: Int, request: SendCommentRequest) = postService.editComment(commentId, request)

    suspend fun deleteComment(commentId: Int) = postService.deleteComment(commentId)

    suspend fun likeComment(commentId: Int) = postService.likeComment(commentId)

    suspend fun unlikeComment(commentId: Int) = postService.unlikeComment(commentId)

    suspend fun bookmarkPost(postId: Int) = postService.bookmarkPost(postId)

    suspend fun unBookmarkPost(postId: Int) = postService.unBookmarkPost(postId)

    suspend fun queryPostBookmark(postId: Int, userId: Int) = postService.queryPostBookmark(postId, userId)

    suspend fun getPostLikes(id: Int, page: Int) = postService.getPostLikes(id, page)
}