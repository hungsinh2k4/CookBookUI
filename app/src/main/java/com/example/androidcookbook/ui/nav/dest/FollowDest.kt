package com.example.androidcookbook.ui.nav.dest

import android.util.Log
import androidx.compose.runtime.collectAsState
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
import com.example.androidcookbook.ui.features.follow.BaseFollowListScreen
import com.example.androidcookbook.ui.features.follow.FollowListScreenType
import com.example.androidcookbook.ui.features.follow.FollowScreenViewModel
import com.example.androidcookbook.ui.features.follow.FollowViewModel
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.navigateToProfile
import kotlin.reflect.typeOf

fun NavGraphBuilder.follow(
    cookbookViewModel: CookbookViewModel,
    navController: NavHostController,
) {
    composable<Routes.Follow>(
        typeMap = mapOf(
            typeOf<User>() to CustomNavTypes.UserType
        )
    ) {
        val targetUser = it.toRoute<Routes.Follow>().user
        val startingScreen = it.toRoute<Routes.Follow>().type

        val currentUser = cookbookViewModel.user.collectAsState().value

        cookbookViewModel.updateCanNavigateBack(true)
        cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
        cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.NoTopBar)

        Log.d("FOLLOW", "LocalViewModelStoreOwner: ${LocalViewModelStoreOwner.current}")

        val followViewModel = hiltViewModel<FollowViewModel, FollowViewModel.FollowViewModelFactory>(
//            it, navController, (
//                if (targetUser.id == currentUser.id) Routes.App.UserProfile(currentUser)
//                else Routes.OtherProfile(targetUser)
//            )
        ) { factory ->
            factory.create(currentUser, targetUser.id)
        }

        val currentUserFollowing = followViewModel.currentUserFollowing.collectAsState().value

        val followScreenViewModel = hiltViewModel<FollowScreenViewModel, FollowScreenViewModel.FollowScreenViewModelFactory>(
//            it, navController, Routes.Follow(targetUser, startingScreen)
        ) { factory ->
            factory.create(startingScreen)
        }

        val screenType = followScreenViewModel.screenType.collectAsState().value

        RefreshableScreen(
            isRefreshing = followViewModel.isRefreshing.collectAsState().value,
            onRefresh = {
                followViewModel.refresh()
            }
        ) {
            BaseFollowListScreen(
                user = targetUser,
                type = screenType,
                list = (
                        if (screenType == FollowListScreenType.Followers) {
                            followViewModel.followers.collectAsState().value
                        } else
                            followViewModel.following.collectAsState().value
                        ),
                onListItemClick = { user ->
                    navController.navigateToProfile(currentUser, user)
                },
                currentUser = currentUser,
                currentUserFollowing = currentUserFollowing,
                onFollowButtonClick = { user ->
                    if (followViewModel.isCurrentUserFollowing(user))
                        followViewModel.unfollowUser(user)
                    else followViewModel.followUser(user)
                },
                onBackButtonClick = {
                    navController.popBackStack()
                },
                onFollowingNavigate = {
                    followScreenViewModel.setScreenType(FollowListScreenType.Following)
                },
                onFollowersNavigate = {
                    followScreenViewModel.setScreenType(FollowListScreenType.Followers)
                }
            )
        }
    }
}