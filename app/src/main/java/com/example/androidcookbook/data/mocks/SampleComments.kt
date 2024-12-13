package com.example.androidcookbook.data.mocks

import com.example.androidcookbook.domain.model.post.Comment

object SampleComments {
    val comments = buildList<Comment> {
        repeat(10) {
            add(
                Comment(
                    id = it,
                )
            )
        }
    }
}