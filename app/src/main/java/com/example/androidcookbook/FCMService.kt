package com.example.androidcookbook

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidcookbook.data.providers.DataStoreManager
import com.example.androidcookbook.ui.CookbookViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    private val dataStoreManager by lazy { DataStoreManager(this) }
    var notice = false

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.canSendNotification.collect {
                notice = it
            }
        }

        // Get the data payload from the message
        val data = message.data

        // Access specific values from the data map by key
        val customKey = data  // Replace "customKey" with the actual key you're expecting

        // You can also handle any other logic with the data you retrieved
        if (customKey != null) {
            // Do something with the customKey value
            Log.d("test",customKey.toString())
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val requestCode = 1

        val channelId = "Firebase Messaging ID"
        val channelName = "Firebase Messaging"
        notificationManager.createNotificationChannel(
            NotificationChannel(channelId, channelName,
                if (notice) NotificationManager.IMPORTANCE_HIGH
                else NotificationManager.IMPORTANCE_NONE
            )
        )
        CookbookViewModel.updateNotificationCount(CookbookViewModel.notificationCount.value+1)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlag)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.cookbook_app_icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}