package com.example.androidcookbook.ui.features.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import com.example.androidcookbook.domain.model.post.Comment
import com.example.androidcookbook.domain.model.user.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    comments: List<Comment>,
    user: User,
    onSendComment: (String) -> Unit,
    onDeleteComment: (Comment) -> Unit,
    onEditComment: (Comment) -> Unit,
    onLikeComment: (Comment) -> Unit,
    onUserClick: (User) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
        ) {
            Text(
                text = "Comments",
                fontWeight = FontWeight.Bold,
            )
            LazyColumn {
                items(comments) { comment ->
                    CommentRow(
                        comment = comment,
                        currentUser = user,
                        onDeleteComment = onDeleteComment,
                        onEditComment = onEditComment,
                        onLikeComment = onLikeComment,
                        onUserClick = onUserClick,
                    )
                }
            }
        }
        HorizontalDivider()

        WriteCommentRow(user, onUserClick, onSendComment)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CommentBottomSheetPreview() {
    CommentBottomSheetTheme {
        CommentBottomSheet(comments = listOf(
            Comment(
                content = "Lorem ipsum dolor sit amet, small consectetur adipiscing elit.",
            ),
            Comment(
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            ),
            Comment(
                content = "Lorem dolor sit amet, small consectetur elit.",
            ),
            Comment(
                content = "Lorem ipsum dolor sit, small consectetur adipiscing elit.",
            ),
            Comment(
                content = "Lorem sit amet, small consectetur adipiscing elit.",
            ),
            Comment(
                content = "Lorem sit amet.",
            ),
            Comment(
                content = "Lorem sit amet, small consectetur adipiscing elit, lorem sit amet, small consectetur adipiscing elit, small,",
            ),
        ), user = User(
            id = 1,
            name = "Username",
        ), onSendComment = {}, {}, {}, {}, onDismiss = {}, sheetState = SheetState(
            skipPartiallyExpanded = true,
            density = Density(LocalContext.current),
            initialValue = SheetValue.Expanded,
            confirmValueChange = { true },
        ),
            onUserClick = {},
            modifier = Modifier
        )
    }
}