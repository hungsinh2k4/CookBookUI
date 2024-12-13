package com.example.androidcookbook.ui.features.userprofile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R

@Composable
fun UserAvatar(
    avatarPath: Any?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
//            painter = painterResource(id = R.drawable.default_avatar),
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarPath)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier =
        modifier
            .size(125.dp)
            .border(shape = CircleShape, width = 5.dp, color = Color.White)
            .padding(5.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.default_avatar),
        error = painterResource(R.drawable.default_avatar),
    )
}