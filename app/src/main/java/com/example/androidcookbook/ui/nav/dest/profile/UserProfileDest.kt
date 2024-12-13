package com.example.androidcookbook.ui.nav.dest.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.common.containers.RefreshableScreen
import com.example.androidcookbook.ui.common.screens.FailureScreen
import com.example.androidcookbook.ui.common.screens.GuestLoginScreen
import com.example.androidcookbook.ui.common.screens.LoadingScreen
import com.example.androidcookbook.ui.features.follow.FollowListScreenType
import com.example.androidcookbook.ui.features.follow.FollowViewModel
import com.example.androidcookbook.ui.features.follow.TabNavigationItem
import com.example.androidcookbook.ui.features.userprofile.GuestProfile
import com.example.androidcookbook.ui.features.userprofile.UserPostState
import com.example.androidcookbook.ui.features.userprofile.UserProfileScreen
import com.example.androidcookbook.ui.features.userprofile.UserProfileUiState
import com.example.androidcookbook.ui.features.userprofile.UserProfileViewModel
import com.example.androidcookbook.ui.features.userprofile.EditProfileButton
import com.example.androidcookbook.ui.features.userprofile.UserPostPortionType
import com.example.androidcookbook.ui.features.userprofile.userPostPortion
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.guestNavToAuth
import kotlin.reflect.typeOf

fun NavGraphBuilder.userProfile(
    cookbookViewModel: CookbookViewModel,
    navController: NavHostController,
) {
    composable<Routes.App.UserProfile>(
        typeMap = mapOf(
            typeOf<User>() to CustomNavTypes.UserType
        )
    ) {
        cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
        cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
        cookbookViewModel.updateCanNavigateBack(false)

        val currentUser = cookbookViewModel.user.collectAsState().value

        if (currentUser.id == GUEST_ID) {
            GuestLoginScreen {
                navController.guestNavToAuth()
            }
            return@composable
        }

        val userId = it.toRoute<Routes.App.UserProfile>().userId

        val userProfileViewModel =
            hiltViewModel<UserProfileViewModel, UserProfileViewModel.UserProfileViewModelFactory>(
//                it, navController, Routes.App
            ) { factory ->
                factory.create(userId)
            }

        val userProfileUiState = userProfileViewModel.uiState.collectAsState().value
        val followViewModel = hiltViewModel<FollowViewModel, FollowViewModel.FollowViewModelFactory>(
//            it, navController, Routes.App.UserProfile(user)
        ) { factory ->
            factory.create(currentUser, userId)
        }

        LaunchedEffect(Unit) {
            userProfileViewModel.refreshNoIndicator()
            followViewModel.refresh()
        }

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
                    val userPostPortionType = userProfileViewModel.userPostPortionType.collectAsState().value
                    val userPostState = userProfileViewModel.userPostState.collectAsState().value

                    UserProfileScreen(
                        user = userProfileUiState.user,
                        headerButton = {
                            EditProfileButton(
                                onEditProfileClick = {
                                    navController.navigate(Routes.EditProfile(userProfileUiState.user))
                                }
                            )
                        },
                        navigateToEditProfile = {
                            navController.navigate(Routes.EditProfile(userProfileUiState.user))
                        },
                        followersCount = followViewModel.followers.collectAsState().value.size,
                        followingCount = followViewModel.following.collectAsState().value.size,
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
//                                    selected = userPostPortionType == UserPostPortionType.Likes,
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
                                        if (user.id != currentUser.id) {
                                            navController.navigate(Routes.OtherProfile(user.id))
                                        }
                                    },
                                    currentUser = userProfileUiState.user,
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