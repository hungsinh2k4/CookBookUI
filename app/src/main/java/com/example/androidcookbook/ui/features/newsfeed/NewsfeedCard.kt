package com.example.androidcookbook.ui.features.newsfeed

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.data.mocks.SampleComments
import com.example.androidcookbook.data.mocks.SamplePosts
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.common.iconbuttons.LikeButton
import com.example.androidcookbook.ui.common.utils.apiDateFormatter
import com.example.androidcookbook.ui.components.post.PostHeader
import com.example.androidcookbook.ui.components.post.PostTitle
import com.example.androidcookbook.ui.features.post.details.PostDetailsViewModel
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme
import java.time.LocalDate

@Composable
fun NewsfeedCard(
    post: Post,
    currentUser: User,
    onEditPost: () -> Unit,
    onDeletePost: () -> Unit,
    onUserClick: (User) -> Unit,
    onSeeDetailsClick: (Post) -> Unit,
    modifier: Modifier = Modifier,
) {
    val postDetailsViewModel = hiltViewModel<PostDetailsViewModel, PostDetailsViewModel.PostDetailsViewModelFactory>(
        key = post.id.toString(),
    ){ factory ->
        factory.create(post.id, currentUser)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PostHeader(
                post.author,
                LocalDate.parse(post.createdAt, apiDateFormatter).toString(),
                onUserClick = onUserClick,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                16.sp
            )
            Spacer(Modifier.weight(1F))
            PostOptionsButton(
                post,
                onEditPost,
                onDeletePost,
                currentUser.id == post.author.id
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            PostTitle(post.title)

            if (post.mainImage != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.mainImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(top = 16.dp, bottom = 8.dp)
                )
            }

            // Post desc
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            )

            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Like button
                LikeButton(
                    postDetailsViewModel.isPostLiked.collectAsState().value,
                    {postDetailsViewModel.togglePostLike()}
                )

                // Bookmark button
                IconButton(onClick = {postDetailsViewModel.togglePostBookmark()}) {
                    Icon(
                        imageVector =
                        if (postDetailsViewModel.isPostBookmarked.collectAsState().value) {
                            Icons.Outlined.Bookmark
                        } else {
                            Icons.Outlined.BookmarkBorder
                        },
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Details »",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .clickable { onSeeDetailsClick(post) }
                )
            }
        }
    }
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier.alpha(0.75F),
    )
}

@Composable
fun PostOptionsButton(
    post: Post,
    onEditPost: () -> Unit,
    onDeletePost: () -> Unit,
    isCurrentUser: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            expanded = true
        },
    ) {
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            contentDescription = "Open Options",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${post.author.name}\n${LocalDate.parse(post.createdAt, apiDateFormatter)}\n${post.title}\n${post.mainImage}\n${post.description}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            val context = LocalContext.current
            DropdownMenuItem(
                text = { Text("Share") },
                onClick = {
                    context.startActivity(shareIntent)
                    expanded = false
                }
            )
            if (isCurrentUser) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {
                        onEditPost()
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        onDeletePost()
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun NewsfeedCardPreview() {
    AndroidCookbookTheme {
        NewsfeedCard(
            post = SamplePosts.posts[0],
            currentUser = SampleComments.comments[0].user,
            onEditPost = {},
            onDeletePost = {},
            onUserClick = {},
            onSeeDetailsClick = {},
            modifier = Modifier
        )
    }
}

