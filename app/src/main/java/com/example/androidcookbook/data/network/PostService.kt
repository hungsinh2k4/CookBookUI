package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.post.GetCommentResponse
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.post.PostCreateRequest
import com.example.androidcookbook.domain.model.post.PostCreateResponse
import com.example.androidcookbook.domain.model.post.PostLikesResponse
import com.example.androidcookbook.domain.model.post.SendCommentRequest
import com.example.androidcookbook.domain.network.BookmarkQueryResponse
import com.example.androidcookbook.domain.network.LikeQueryResponse
import com.example.androidcookbook.domain.network.SuccessMessageBody
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostService {
    @POST("posts")
    suspend fun createPost(@Body post: PostCreateRequest): ApiResponse<PostCreateResponse>

    @GET("posts/{postId}")
    suspend fun getPost(@Path("postId") postId: Int): ApiResponse<Post>

    @GET("like/check/{postId}/{userId}")
    suspend fun queryPostLike(
        @Path("postId") postId: Int,
        @Path("userId") userId: Int,
    ): ApiResponse<LikeQueryResponse>

    @POST("like/{postId}")
    suspend fun likePost(@Path("postId") postId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("like/{postId}")
    suspend fun unlikePost(@Path("postId") postId: Int): ApiResponse<SuccessMessageBody>

    @GET("comment-with-like/{postId}/{userId}/{page}")
    suspend fun getComments(@Path("postId") postId: Int, @Path("userId") userId: Int, @Path("page") page: Int): ApiResponse<GetCommentResponse>

    @POST("comment/{postId}")
    suspend fun sendComment(@Path("postId") postId: Int, @Body request: SendCommentRequest): ApiResponse<SuccessMessageBody>

    @PUT("comment/{commentId}")
    suspend fun editComment(@Path("commentId") commentId: Int, @Body request: SendCommentRequest): ApiResponse<SuccessMessageBody>

    @DELETE("comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int): ApiResponse<SuccessMessageBody>

    @POST("comment/like/{commentId}")
    suspend fun likeComment(@Path("commentId") commentId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("comment/like/{commentId}")
    suspend fun unlikeComment(@Path("commentId") commentId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("posts/{postId}")
    suspend fun deletePost(@Path("postId")postId: Int): ApiResponse<SuccessMessageBody>

    @PUT("posts/{postId}")
    suspend fun updatePost(@Path("postId") postId: Int, @Body post: PostCreateRequest): ApiResponse<PostCreateResponse>

    @POST("favorite/{postId}")
    suspend fun bookmarkPost(@Path("postId") postId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("favorite/{postId}")
    suspend fun unBookmarkPost(@Path("postId") postId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("favorite/check/{postId}/{userId}")
    suspend fun queryPostBookmark(
        @Path("postId") postId: Int,
        @Path("userId") userId: Int
    ): ApiResponse<BookmarkQueryResponse>

    @GET("like/{postId}/{page}")
    suspend fun getPostLikes(@Path("postId") postId: Int, @Path("page") page: Int): ApiResponse<PostLikesResponse>
}