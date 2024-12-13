package com.example.androidcookbook.ui.common.iconbuttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidcookbook.ui.features.post.details.OutlinedIconButton

@Composable
fun LikeButton(
    isLiked: Boolean,
    onLikedClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedIconButton(
        icon = (
                if (isLiked) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Outlined.FavoriteBorder
                }
                ),
        onclick = onLikedClick,
        modifier = modifier
    )
}

@Composable
@Preview
fun LikeButtonFalsePreview() {
    LikeButton(isLiked = false, onLikedClick = {})
}

@Composable
@Preview
fun LikeButtonTruePreview() {
    LikeButton(isLiked = true, onLikedClick = {})
}