package com.example.androidcookbook.domain.model.user

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

const val GUEST_ID = 0

@Serializable
data class User(
    @SerializedName(value = "id", alternate = ["userId"]) val id: Int = GUEST_ID,
    val bio: String? = "",
    val name: String = "Guest",
    val avatar: String? = null,
    val banner: String? = null,
    val totalFollowers: Int = 0,
    val totalFollowing: Int = 0,
)
