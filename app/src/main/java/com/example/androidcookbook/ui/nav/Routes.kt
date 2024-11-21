package com.example.androidcookbook.ui.nav

import kotlinx.serialization.Serializable

@Serializable
object Routes {

    @Serializable
    object Auth {
        @Serializable
        data object Login

        @Serializable
        data object Register

        @Serializable
        data object ForgotPassword
    }

    @Serializable
    object App {
        @Serializable
        data object Category

        @Serializable
        data object AIChat

        @Serializable
        data object Newsfeed

        @Serializable
        data class UserProfile(val userId: Int)
    }

    @Serializable
    data object Search
    @Serializable
    data object CreatePost

    @Serializable
    object DialogDestination
}
