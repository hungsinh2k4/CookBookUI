package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.notification.GetNotificationsResponse
import com.example.androidcookbook.domain.network.SuccessMessageBody
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationService {
    @GET("notifications/{page}")
    suspend fun getNotifications(@Path("page") page: Int): ApiResponse<GetNotificationsResponse>

    @PUT("notifications/read/{notificationId}")
    suspend fun readNotification(@Path("notificationId") notificationId: Int): ApiResponse<SuccessMessageBody>

    @DELETE("notifications/{notificationId}")
    suspend fun clearNotification(@Path("notificationId") notificationId: Int): ApiResponse<SuccessMessageBody>
}