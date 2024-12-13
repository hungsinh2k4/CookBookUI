package com.example.androidcookbook.ui.features.newsfeed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.data.mocks.SamplePosts
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.common.screens.LoadingScreen
import com.example.androidcookbook.ui.components.EndlessLazyColumn
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun NewsfeedScreen(
    posts: List<Post>,
    currentUser: User,
    onEditPost: (Post) -> Unit,
    onDeletePost: (Post) -> Unit,
    onSeeDetailsClick: (Post) -> Unit,
    onUserClick: (User) -> Unit,
    onLoadMore: () -> Unit,
    isLoadingMore: Boolean,
    modifier: Modifier = Modifier,
) {
    EndlessLazyColumn(
        items = posts,
        itemKey = { post -> post.id },
        loadMore = onLoadMore,
        isLoadingMore = isLoadingMore,
        modifier = modifier,
        loadMoreLimit = 5
    ) { post ->
        NewsfeedCard(
            post = post,
            currentUser = currentUser,
            onEditPost = { onEditPost(post) },
            onDeletePost = { onDeletePost(post) },
            onUserClick = onUserClick,
            onSeeDetailsClick = onSeeDetailsClick,
        )
    }


}

@Composable
@Preview
fun NewsfeedScreenPreview() {
    AndroidCookbookTheme(darkTheme = false) {
        NewsfeedScreen(
            posts = SamplePosts.posts,
            currentUser = User(),
            {}, {}, {}, {}, {}, false,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
@Preview
fun NewsfeedScreenPreviewDarkTheme() {
    AndroidCookbookTheme(darkTheme = true) {
        NewsfeedScreen(
            posts = SamplePosts.posts,
            currentUser = User(),
            {}, {}, {}, {}, {}, false,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
