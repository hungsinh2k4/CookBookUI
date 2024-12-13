package com.example.androidcookbook.ui.features.userprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidcookbook.domain.model.user.User

@Composable
fun GuestProfile(
    text: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            UserProfileHeader(
                bannerPath = null,
                avatarPath = null,
                navigateToEditProfile = {},
                headerButton = {},
            )
            UserInfo(User(), 0, 0,{}, {}, {})
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text)
            }
        }
    }
}