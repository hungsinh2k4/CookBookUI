package com.example.androidcookbook.ui.components.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R
import com.example.androidcookbook.domain.model.user.User

@Composable
fun SmallAvatar(
    author: User,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(author.avatar)
            .crossfade(true)
            .build(),
//            imageVector = Icons.Default.AccountCircle,
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable { onUserClick(author) },
        contentScale = ContentScale.Crop,
//            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
        error = painterResource(R.drawable.default_avatar),
        placeholder = painterResource(R.drawable.default_avatar)
    )
}

@Composable
fun SmallAvatar(
    avatar: Any?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatar)
            .crossfade(true)
            .build(),
//            imageVector = Icons.Default.AccountCircle,
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
//            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
        error = painterResource(R.drawable.default_avatar),
        placeholder = painterResource(R.drawable.default_avatar)
    )
}