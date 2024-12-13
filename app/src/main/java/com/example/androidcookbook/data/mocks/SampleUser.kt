package com.example.androidcookbook.data.mocks

import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.model.user.User

object SampleUser {
    val users = buildList<User> {
        repeat(10) {
            add (
                User(
                    id = GUEST_ID + it,
                    name = "Ly Duc",
                    avatar = null,
                    bio = if (it % 2 == 0) "I like suffering I like suffering I like sufferingI like sufferingI like sufferingI like sufferingI like suffering" else "I like suffering\nand eating and drinking and playing video games",
                    totalFollowers = 0,
                    totalFollowing = 1
                )
            )
        }
    }
}