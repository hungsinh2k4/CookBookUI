package com.example.androidcookbook.ui.nav.dest.post

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.androidcookbook.data.providers.ThemeType
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.common.containers.RefreshableScreen
import com.example.androidcookbook.ui.common.screens.FailureScreen
import com.example.androidcookbook.ui.common.screens.GuestLoginScreen
import com.example.androidcookbook.ui.common.screens.LoadingScreen
import com.example.androidcookbook.ui.components.post.SmallAvatar
import com.example.androidcookbook.ui.features.comment.CommentBottomSheet
import com.example.androidcookbook.ui.features.comment.CommentBottomSheetTheme
import com.example.androidcookbook.ui.features.comment.EditCommentBottomSheet
import com.example.androidcookbook.ui.features.post.details.EditCommentState
import com.example.androidcookbook.ui.features.post.details.PostDetailsScreen
import com.example.androidcookbook.ui.features.post.details.PostDetailsViewModel
import com.example.androidcookbook.ui.features.post.details.PostUiState
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.guestNavToAuth
import com.example.androidcookbook.ui.nav.utils.navigateToProfile
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.postDetails(viewModel: CookbookViewModel, navController: NavHostController) {
    composable<Routes.App.PostDetails>(
        typeMap = mapOf(
            typeOf<Post>() to CustomNavTypes.PostType,
        )
    ) { backStackEntry ->
        viewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
        viewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
        viewModel.updateCanNavigateBack(true)

        val currentUser = viewModel.user.collectAsState().value

        val darkTheme = when(viewModel.themeType.collectAsState().value) {
            ThemeType.Default -> isSystemInDarkTheme()
            ThemeType.Dark -> true
            ThemeType.Light -> false
        }

        val postRoute = backStackEntry.toRoute<Routes.App.PostDetails>()

        val postDetailsViewModel =
            hiltViewModel<PostDetailsViewModel, PostDetailsViewModel.PostDetailsViewModelFactory> { factory ->
                factory.create(postRoute.postId, viewModel.user.value)
            }

        LaunchedEffect(Unit) {
            postDetailsViewModel.getPost()
            postDetailsViewModel.getPostLikes()
            postDetailsViewModel.getComments(true)
        }

        LaunchedEffect(postDetailsViewModel.isPostLiked.collectAsState().value) {
            postDetailsViewModel.getPostLikes()
        }

        when (val postUiState = postDetailsViewModel.postUiState.collectAsState().value) {
            is PostUiState.Success -> {
                RefreshableScreen(
                    isRefreshing = postDetailsViewModel.isRefreshing.collectAsState().value,
                    onRefresh = { postDetailsViewModel.refresh() }
                ) {
                    PostDetailsScreen(
                        post = postUiState.post,
                        showPostOptions = (viewModel.user.collectAsState().value.id == postUiState.post.author.id),
                        comments = postDetailsViewModel.commentsFlow.collectAsState().value,
                        onEditPost = {
                            navController.navigate(Routes.UpdatePost(postUiState.post))
                        },
                        onDeletePost = {
                            postDetailsViewModel.deletePost(
                                onSuccessNavigate = {
                                    navController.navigateUp()
                                }
                            )
                        },
                        onCommentClick = {
                            postDetailsViewModel.updateShowBottomCommentSheet(true)
                        },
                        currentUser = viewModel.user.collectAsState().value,
                        onDeleteComment = { comment ->
                            postDetailsViewModel.deleteComment(comment)
                        },
                        onEditComment = { comment ->
                            postDetailsViewModel.enterEditCommentState(comment)
                        },
                        onLikeComment = { comment ->
                            postDetailsViewModel.toggleLikeComment(comment)
                        },
                        onSendComment = { content ->
                            postDetailsViewModel.sendComment(content = content)
                        },
                        isLiked = postDetailsViewModel.isPostLiked.collectAsState().value,
                        onLikedClick = {
                            postDetailsViewModel.togglePostLike()
                        },
                        isBookmarked = postDetailsViewModel.isPostBookmarked.collectAsState().value,
                        onBookmarkClick = {
                            postDetailsViewModel.togglePostBookmark()
                        },
                        onUserClick = { user ->
                            navController.navigateToProfile(viewModel.user.value, user)
                        },
                        postLikes = postDetailsViewModel.postLikes.collectAsState().value,
                        onLikeCountClick = {
                            postDetailsViewModel.updateShowLikeCountDialog(true)
                        },
                        modifier = Modifier
                    )

                    if (postDetailsViewModel.showBottomCommentSheet.collectAsState().value) {
                        val sheetState = rememberModalBottomSheetState(
                            skipPartiallyExpanded = true
                        )
                        val scope = rememberCoroutineScope()
                        CommentBottomSheetTheme(darkTheme) {
                            CommentBottomSheet(
                                comments = postDetailsViewModel.commentsFlow.collectAsState().value,
                                user = viewModel.user.collectAsState().value,
                                onSendComment = { content ->
                                    postDetailsViewModel.sendComment(content)
                                },
                                onEditComment = { comment ->
                                    postDetailsViewModel.enterEditCommentState(comment)
                                },
                                onDeleteComment = { comment ->
                                    postDetailsViewModel.deleteComment(comment)
                                },
                                onLikeComment = { comment ->
                                    postDetailsViewModel.toggleLikeComment(comment)
                                },
                                onDismiss = {
                                    postDetailsViewModel.updateShowBottomCommentSheet(false)
                                },
                                onUserClick = { user ->
                                    navController.navigate(Routes.App.UserProfile(user.id))
                                },
                                sheetState = sheetState,
                                modifier = Modifier
                            )
                        }
                    }
                    when (val editCommentState =
                        postDetailsViewModel.editCommentState.collectAsState().value) {
                        is EditCommentState.Editing -> {
                            val sheetState = rememberModalBottomSheetState(
                                skipPartiallyExpanded = true
                            )
                            val scope = rememberCoroutineScope()
                            CommentBottomSheetTheme(darkTheme) {
                                EditCommentBottomSheet(
                                    comment = editCommentState.comment,
                                    user = viewModel.user.collectAsState().value,
                                    onEditCommentSend = { content ->
                                        postDetailsViewModel.editComment(content)
                                    },
                                    onDismiss = {
                                        postDetailsViewModel.exitEditCommentState()
                                    },
                                    sheetState = sheetState,
                                    modifier = Modifier
                                )
                            }
                        }

                        is EditCommentState.NotEditing -> {}
                    }
                    if (postDetailsViewModel.showLikeCountDialog.collectAsState().value) {
                        Dialog(
                            onDismissRequest = {
                                postDetailsViewModel.updateShowLikeCountDialog(false)
                            }
                        ) {
                            val config = LocalConfiguration.current
                            val screenWidth = config.screenWidthDp.dp
                            val screenHeight = config.screenHeightDp.dp
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .heightIn(min = screenHeight.times(0.3f), max = screenHeight.times(0.3f))
                            ) {
                                val postLikes = postDetailsViewModel.postLikes.collectAsState().value
                                if (postLikes.isEmpty()) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = "No one liked this post yet.",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                        )
                                    }
                                    return@Card
                                }
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize().padding(vertical = 8.dp)
                                ) {
                                    items(
                                        items = postLikes,
                                        key = { it.id }
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                                .clickable {
                                                    navController.navigateToProfile(currentUser = currentUser, targetUser = it)
                                                    postDetailsViewModel.updateShowLikeCountDialog(false)
                                                }
                                        ) {
                                            SmallAvatar(it.avatar)
                                            Text(
                                                text = it.name,
                                                modifier = Modifier.padding(start = 8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is PostUiState.Error -> {
                FailureScreen(
                    message = postUiState.message,
                    onRetryClick = { postDetailsViewModel.refresh() }
                )
            }

            is PostUiState.Loading -> {
                LoadingScreen()
            }
            is PostUiState.Guest -> GuestLoginScreen {
                navController.guestNavToAuth()
            }
        }
    }
}

