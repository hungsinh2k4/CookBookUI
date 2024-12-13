package com.example.androidcookbook.ui.common.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import com.example.androidcookbook.ui.components.post.SmallAvatar

@Composable
fun OverlapCircleImage(
    data: Any?,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .clip(CircleShape)
    ) {
        SmallAvatar(data, modifier = Modifier.scale(0.75f))
    }
}