package com.example.androidcookbook.ui.features.follow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.data.mocks.SampleUser
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.components.post.SmallAvatar
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun FollowingListItem(
    user: User,
    onClick: (User) -> Unit,
    followButtonState: FollowButtonState,
    onFollowButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        SmallAvatar(
            author = user,
            onUserClick = onClick,
            modifier = Modifier
        )
        Column(
            Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .weight(1F),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = user.name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable { onClick(user) }

            )
            Text(
                text = user.totalFollowers.toString() + (if (user.totalFollowers > 1)
                    " followers" else " follower"
                ),
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
        FollowButton(
            onFollowButtonClick = onFollowButtonClick,
            followButtonState = followButtonState,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
@Preview
fun FollowingListItemDarkPreview() {
    AndroidCookbookTheme(darkTheme = true) {
        FollowingListItem(
            user = SampleUser.users[0],
            onClick = {},
            followButtonState = FollowButtonState.Follow,
            onFollowButtonClick = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FollowingListItemPreview() {
    AndroidCookbookTheme {
        FollowingListItem(
            user = SampleUser.users[0].copy(
                bio = "I like suffering\nand eating and drinking and playing video games"
            ),
            onClick = {},
            followButtonState = FollowButtonState.Follow,
            onFollowButtonClick = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FollowingListItemNotDisplayedPreview() {
    AndroidCookbookTheme {
        FollowingListItem(
            user = SampleUser.users[0].copy(
                bio = "I like suffering\nand eating and drinking and playing video games"
            ),
            onClick = {},
            followButtonState = FollowButtonState.NotDisplayed,
            onFollowButtonClick = {},
        )
    }
}