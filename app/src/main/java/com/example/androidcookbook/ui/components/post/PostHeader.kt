package com.example.androidcookbook.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun PostHeader(
    author: User,
    createdAt: String?,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier,
    authorFontSize: TextUnit = 19.sp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        SmallAvatar(
            author = author,
            onUserClick = onUserClick,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.width(8.dp)) // Spacing between icon and username
        Column {
            val roundedCornerShape = RoundedCornerShape(45)
            val leftPad = 4.dp
            Box (
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = roundedCornerShape
                    )
                    .clip(roundedCornerShape)
                    .clickable {
                        onUserClick(author)
                    }
            ) {
                Text(
                    text = author.name,
                    fontSize = authorFontSize,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = leftPad)
                )
            }
            Text(
                text = createdAt ?: "",
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = leftPad)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PostHeaderPreview() {
    AndroidCookbookTheme {
        Column(
            modifier = Modifier
                .height(250.dp)
        ) {
            PostHeader(
                author = User(),
                createdAt = "2024-01-01",
                onUserClick = {},
                authorFontSize = 19.sp,
            )
        }
    }
}