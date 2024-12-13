package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.NotificationService
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationService: NotificationService
) {
    suspend fun getNotifications(page: Int) = notificationService.getNotifications(page)

    suspend fun readNotification(notificationId: Int) = notificationService.readNotification(notificationId)

    suspend fun clearNotification(notificationId: Int) = notificationService.clearNotification(notificationId)
}