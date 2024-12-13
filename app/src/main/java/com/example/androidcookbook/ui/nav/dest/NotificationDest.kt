package com.example.androidcookbook.ui.nav.dest

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidcookbook.domain.model.notification.NotificationType
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.common.appbars.AppBarTheme
import com.example.androidcookbook.ui.common.containers.RefreshableScreen
import com.example.androidcookbook.ui.common.screens.FailureScreen
import com.example.androidcookbook.ui.common.screens.GuestLoginScreen
import com.example.androidcookbook.ui.common.screens.LoadingScreen
import com.example.androidcookbook.ui.common.state.ScreenUiState
import com.example.androidcookbook.ui.features.notification.NotificationScreen
import com.example.androidcookbook.ui.features.notification.NotificationScreenTopBar
import com.example.androidcookbook.ui.getDarkThemeConfig
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.guestNavToAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

fun NavGraphBuilder.notification(
    cookbookViewModel: CookbookViewModel,
    navController: NavHostController,
) {
    composable<Routes.Notifications> {
        var isClearing by remember { mutableStateOf(false) }

        if(!cookbookViewModel.isTopBarSet.collectAsState().value) {
            cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {
                AppBarTheme(getDarkThemeConfig()) {
                    NotificationScreenTopBar(
                        onBackButtonClick =
                        {
                            cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
                            cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
                            cookbookViewModel.updateCanNavigateBack(false)
                            cookbookViewModel.setTopBarState(true)
                            navController.navigateUp()
                        },
                        onClearAllClick = {
                            isClearing = true
                        }
                    )
                }
            })
            cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
            cookbookViewModel.updateCanNavigateBack(true)
        }

        val notificationViewModel = hiltViewModel<NotificationViewModel>()



        val uiState = notificationViewModel.notificationUiState.collectAsState().value
        Log.d("Notification", "notificationUiState: $uiState")

        LaunchedEffect(isClearing) {
            if (isClearing) {
                notificationViewModel.clearAllNotifications()
                delay(600)
                notificationViewModel.updateEmpty()
                isClearing = false
            }
        }

        if (cookbookViewModel.user.collectAsState().value.id == GUEST_ID) {
            GuestLoginScreen {
                navController.guestNavToAuth()
            }
            return@composable
        }

        LaunchedEffect(key1 = Unit) {
            notificationViewModel.refresh()
        }

        when (uiState) {
            is ScreenUiState.Failure -> FailureScreen(uiState.message) { notificationViewModel.refresh() }
            ScreenUiState.Guest -> GuestLoginScreen { navController.guestNavToAuth() }
            ScreenUiState.Loading -> LoadingScreen()
            is ScreenUiState.Success ->
                RefreshableScreen(
                    isRefreshing = notificationViewModel.isRefreshing.collectAsState().value,
                    onRefresh = { notificationViewModel.refresh() },
                ) {
                    NotificationScreen(
                        notifications = uiState.data,
                        onNotificationClick = { notification ->
                            notificationViewModel.markRead(notification.id)
                            when (notification.type) {
                                NotificationType.NEW_FOLLOWER -> navController.navigate(Routes.OtherProfile(notification.relatedId))
                                NotificationType.NEW_POST_LIKE -> navController.navigate(Routes.App.PostDetails(notification.relatedId))
                                NotificationType.NEW_POST_COMMENT -> navController.navigate(Routes.App.PostDetails(notification.relatedId))
                                NotificationType.NEW_COMMENT_LIKE -> navController.navigate(Routes.App.PostDetails(notification.relatedId))
                            }
                        },
                        loadMore = { notificationViewModel.loadMore() },
                        isClearing = isClearing
                    )
                }
        }
    }
}

