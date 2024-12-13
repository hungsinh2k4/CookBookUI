package com.example.androidcookbook.ui.features.userprofile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R

@Composable
fun UserBanner(
    bannerPath: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
//        painter = painterResource(id = R.drawable.image_5),
        model = ImageRequest.Builder(LocalContext.current)
            .data(bannerPath)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier =
        modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = contentScale,
        error = painterResource(id = R.drawable.image_5),
        placeholder = painterResource(id = R.drawable.image_5),
    )
}