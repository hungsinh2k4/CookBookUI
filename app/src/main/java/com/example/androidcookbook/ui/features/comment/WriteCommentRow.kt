package com.example.androidcookbook.ui.features.comment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.components.post.SmallAvatar
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme
import com.example.androidcookbook.ui.theme.transparentTextFieldColors

@Composable
fun WriteCommentRow(
    user: User,
    onUserClick: (User) -> Unit,
    onSendComment: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp)
    ) {
        var commentContent by remember { mutableStateOf("") }

        SmallAvatar(
            author = user,
            onUserClick = onUserClick,
        )
        Spacer(Modifier.width(8.dp))
        TextField(
            value = commentContent,
            onValueChange = { commentContent = it },
            placeholder = {
                Text("Write a comment...")
            },
            colors = transparentTextFieldColors(),
            modifier = Modifier.weight(1F)
        )
        IconButton(
            onClick = {
                onSendComment(commentContent)
                commentContent = ""
                focusManager.clearFocus()
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
@Preview
private fun WriteCommentRowPreview() {
    AndroidCookbookTheme {
        WriteCommentRow(
            user = User(),
            onUserClick = {},
            onSendComment = {},
        )
    }
}