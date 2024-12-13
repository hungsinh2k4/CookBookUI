package com.example.androidcookbook.ui.features.follow

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class FollowButtonState {
    Follow,
    Following,
    NotDisplayed
}

@Composable
fun FollowButton(
    onFollowButtonClick: () -> Unit,
    followButtonState: FollowButtonState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        when (followButtonState) {
            FollowButtonState.NotDisplayed ->
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(45.dp)
                )
            FollowButtonState.Following ->
                Button (
                    onClick = onFollowButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
                ) {
                    Text(
                        text = "Following",
                    )
                }

            FollowButtonState.Follow ->
                Button(
                    onClick = onFollowButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface
                    )
                ) {
                    Text(
                        text = "Follow",
                    )
                }
        }
    }
}

@Composable
@Preview
fun FollowButtonPreview() {
    FollowButton(
        onFollowButtonClick = {},
        followButtonState = FollowButtonState.Follow
    )
}

@Composable
@Preview(showBackground = true)
fun FollowButtonNotDisplayedPreview() {
    FollowButton(
        onFollowButtonClick = {},
        followButtonState = FollowButtonState.NotDisplayed
    )
}