package com.example.androidcookbook.ui.nav

import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.features.follow.FollowListScreenType
import kotlinx.serialization.Serializable

@Serializable
object Routes {

    @Serializable
    object Auth {

        @Serializable
        object Login

        @Serializable
        object Register

        @Serializable
        object ForgotPassword {

            @Serializable
            object Screen

            @Serializable
            object Otp

            @Serializable
            object Reset
        }
    }

    @Serializable
    object App {

        @Serializable
        object Category

        @Serializable
        object AIChef

        @Serializable
        object Newsfeed

        @Serializable
        data class UserProfile(val userId: Int)

        @Serializable
        data class PostDetails(val postId: Int)
    }

    @Serializable
    object DialogDestination

    @Serializable
    object Search

    @Serializable
    object CreatePost

    @Serializable
    data class UpdatePost(val post: Post)

    @Serializable
    data class EditProfile(val user: User)

    @Serializable
    data class OtherProfile(val userId: Int)

    @Serializable
    data class Follow(val user: User, val type: FollowListScreenType)

    @Serializable
    object Settings

    @Serializable
    object Notifications
}
