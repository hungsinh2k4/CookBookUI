package com.example.androidcookbook.ui.nav.dest.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.common.containers.RefreshableScreen
import com.example.androidcookbook.ui.common.screens.FailureScreen
import com.example.androidcookbook.ui.common.screens.LoadingScreen
import com.example.androidcookbook.ui.features.follow.FollowListScreenType
import com.example.androidcookbook.ui.features.follow.FollowViewModel
import com.example.androidcookbook.ui.features.userprofile.GuestProfile
import com.example.androidcookbook.ui.features.userprofile.UserPostState
import com.example.androidcookbook.ui.features.userprofile.UserProfileScreen
import com.example.androidcookbook.ui.features.userprofile.UserProfileUiState
import com.example.androidcookbook.ui.features.userprofile.UserProfileViewModel
import com.example.androidcookbook.ui.features.follow.FollowButton
import com.example.androidcookbook.ui.features.follow.FollowButtonState
import com.example.androidcookbook.ui.features.follow.TabNavigationItem
import com.example.androidcookbook.ui.features.userprofile.UserPostPortionType
import com.example.androidcookbook.ui.features.userprofile.userPostPortion
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.navigateToProfile
import kotlin.reflect.typeOf

fun NavGraphBuilder.otherProfile(
    cookbookViewModel: CookbookViewModel,
    currentUser: User,
    navController: NavHostController,
) {
    composable<Routes.OtherProfile>(
        typeMap = mapOf(
            typeOf<User>() to CustomNavTypes.UserType
        )
    ) {
        val userId = it.toRoute<Routes.OtherProfile>().userId

        cookbookViewModel.updateCanNavigateBack(true)
        cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
        cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)

        Log.d("UserProfileViewModelOwner", "LocalViewModelStoreOwner: ${LocalViewModelStoreOwner.current}")

        val userProfileViewModel =
            hiltViewModel<UserProfileViewModel, UserProfileViewModel.UserProfileViewModelFactory>(

            ) { factory ->
                factory.create(userId)
            }

        val followViewModel = hiltViewModel<FollowViewModel, FollowViewModel.FollowViewModelFactory>(
//            it, navController, Routes.OtherProfile(user)
        ) { factory ->
            factory.create(
                currentUser,
                userId,
            )
        }

        LaunchedEffect(Unit) {
            userProfileViewModel.refreshNoIndicator()
            followViewModel.refresh()
        }

        val userProfileUiState = userProfileViewModel.uiState.collectAsState().value

        RefreshableScreen(
            isRefreshing = userProfileViewModel.isRefreshing.collectAsState().value,
            onRefresh = {
                userProfileViewModel.refresh()
                followViewModel.refresh()
            }
        ) {
            when (userProfileUiState) {
                is UserProfileUiState.Loading -> {
                    LoadingScreen()
                }

                is UserProfileUiState.Success -> {
                    var userPostPortionType = userProfileViewModel.userPostPortionType.collectAsState().value
                    val userPostState = userProfileViewModel.userPostState.collectAsState().value

                    Log.d("UserProfileViewModelOwner", "LocalViewModelStoreOwner: ${LocalViewModelStoreOwner.current}")


                    val isFollowing = followViewModel.isFollowing.collectAsState().value
                    UserProfileScreen(
                        user = userProfileUiState.user,
                        headerButton = {
                            FollowButton(
                                onFollowButtonClick = {
                                    followViewModel.toggleFollow(userProfileUiState.user)
                                },
                                followButtonState = (
                                    if (isFollowing) FollowButtonState.Following
                                    else FollowButtonState.Follow
                                )
                            )
                        },
                        followersCount = followViewModel.followers.collectAsState().value.size,
                        followingCount = followViewModel.following.collectAsState().value.size,
                        navigateToEditProfile = {},
                        onFollowersClick = {
                            navController.navigate(
                                Routes.Follow(userProfileUiState.user, FollowListScreenType.Followers)
                            )
                        },
                        onFollowingClick = {
                            navController.navigate(
                                Routes.Follow(userProfileUiState.user, FollowListScreenType.Following)
                            )
                        },
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(50.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TabNavigationItem(
                                    text = "Posts",
                                    selected = userPostPortionType == UserPostPortionType.Posts,
                                    onClick = {
                                        userProfileViewModel.setUserPostPortionType(UserPostPortionType.Posts)
                                        userProfileViewModel.getUserPosts(userId)
                                    },
                                    modifier = Modifier
                                        .weight(1F)
                                )
//                                TabNavigationItem(
//                                    text = "Likes",
//                                    selected = type == UserPostPortionType.Likes,
//                                    onClick = { userProfileViewModel.setUserPostPortionType(UserPostPortionType.Likes) },
//                                    modifier = Modifier
//                                        .weight(1F)
//                                )
                                TabNavigationItem(
                                    text = "Favorites",
                                    selected = userPostPortionType == UserPostPortionType.Favorites,
                                    onClick = {
                                        userProfileViewModel.setUserPostPortionType(UserPostPortionType.Favorites)
                                        userProfileViewModel.getUserFavoritePosts()
                                    },
                                    modifier = Modifier
                                        .weight(1F)
                                )
                            }
                            HorizontalDivider()
                        }
                        when (userPostState) {
                            is UserPostState.Loading -> item {
                                Spacer(Modifier.height(40.dp))
                                LoadingScreen()
                            }

                            is UserPostState.Success -> {
                                userPostPortion(
                                    userPosts = userPostState.userPosts,
                                    onEditPost = { post ->
                                        navController.navigate(Routes.UpdatePost(post))
                                    },
                                    onDeletePost = { post ->
                                        userProfileViewModel.deletePost(post)
                                    },
                                    onPostSeeDetailsClick = { post ->
                                        navController.navigate(Routes.App.PostDetails(post.id))
                                    },
                                    onUserClick = { user ->
                                        if (user.id != userProfileUiState.user.id) {
                                            navController.navigateToProfile(currentUser, user)
                                        }
                                    },
                                    currentUser = currentUser,
                                    type = userPostPortionType,
                                )
                            }

                            is UserPostState.Failure -> item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text("Failed to fetch user ${userPostPortionType.name.lowercase()}.")
                                }
                            }

                            is UserPostState.Guest -> item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text("Login to see your posts.")
                                }
                            }
                        }
                    }
                }

                is UserProfileUiState.Failure -> {
                    FailureScreen(
                        message = userProfileUiState.message,
                        onRetryClick = {
                            userProfileViewModel.refresh()
                            followViewModel.refresh()
                        }
                    )
                }

                is UserProfileUiState.Guest -> {
                    GuestProfile("Login to see your posts.")
                }
            }
        }
    }
}

