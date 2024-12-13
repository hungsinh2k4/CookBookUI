package com.example.androidcookbook.ui.features.comment

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.post.Comment
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.components.post.SmallAvatar
import com.example.androidcookbook.ui.theme.transparentTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCommentBottomSheet(
    comment: Comment,
    user: User,
    onEditCommentSend: (String) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier
            .safeDrawingPadding()
            .fillMaxSize(),
        dragHandle = null,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Edit comment",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            HorizontalDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                var commentContent by remember { mutableStateOf(comment.content) }

                SmallAvatar(
                    author = user,
                    onUserClick = {},
                )
                Spacer(Modifier.width(8.dp))
                TextField(
                    value = commentContent,
                    onValueChange = { commentContent = it },
                    colors = transparentTextFieldColors(),
                    modifier = Modifier.weight(1F)
                        .focusRequester(focusRequester)
                )
                IconButton(
                    onClick = { onEditCommentSend(commentContent) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun EditCommentBottomSheetPreview() {
    CommentBottomSheetTheme {
        EditCommentBottomSheet(
            comment = Comment(
                id = 0,
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            ),
            user = User(),
            onEditCommentSend = {},
            onDismiss = {},
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                density = Density(LocalContext.current),
                initialValue = SheetValue.Expanded,
                confirmValueChange = { true }
            ),
            modifier = Modifier
        )
    }
}