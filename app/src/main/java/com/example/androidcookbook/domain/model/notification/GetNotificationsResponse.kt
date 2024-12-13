package com.example.androidcookbook.domain.model.notification

data class GetNotificationsResponse(
    val nextPage: Boolean,
    val notifications: List<Notification>,
)
