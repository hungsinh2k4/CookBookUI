package com.example.androidcookbook.ui.nav.graphs

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.features.aigen.AIGenScreen
import com.example.androidcookbook.ui.features.aigen.AiGenViewModel
import com.example.androidcookbook.ui.features.aigen.AiScreenTheme
import com.example.androidcookbook.ui.features.category.CategoryScreen
import com.example.androidcookbook.ui.features.category.CategoryViewModel
import com.example.androidcookbook.ui.features.newsfeed.NewsfeedScreen
import com.example.androidcookbook.ui.features.newsfeed.NewsfeedViewModel
import com.example.androidcookbook.ui.features.userprofile.GuestProfile
import com.example.androidcookbook.ui.features.userprofile.UserPostState
import com.example.androidcookbook.ui.features.userprofile.UserProfileScreen
import com.example.androidcookbook.ui.features.userprofile.UserProfileUiState
import com.example.androidcookbook.ui.features.userprofile.UserProfileViewModel
import com.example.androidcookbook.ui.features.userprofile.userPostPortion
import com.example.androidcookbook.ui.getDarkThemeConfig
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.dest.newsfeed
import com.example.androidcookbook.ui.nav.dest.profile.userProfile
import com.example.androidcookbook.ui.nav.utils.sharedViewModel
import kotlin.reflect.typeOf

/**
 * App screens nav graph builder
 */
fun NavGraphBuilder.appScreens(
    navController: NavHostController,
    cookbookViewModel: CookbookViewModel,
) {
    navigation<Routes.App>(
        startDestination = Routes.App.Newsfeed
    ) {
        composable<Routes.App.Category>() {
            val categoryViewModel: CategoryViewModel =
                sharedViewModel(it, navController, Routes.App)

            CategoryScreen(
                categoryViewModel,
                cookbookViewModel = cookbookViewModel,
            )
        }
        composable<Routes.App.AIChef> {

//            cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
//            cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
//            cookbookViewModel.updateCanNavigateBack(false)

            val aiGenViewModel = sharedViewModel<AiGenViewModel>(
                it, navController, Routes.App
            )

            AiScreenTheme(getDarkThemeConfig()) {
                AIGenScreen(
                    aiGenViewModel,
                    cookbookViewModel
                )
            }
        }
        newsfeed(cookbookViewModel, navController)

        userProfile(cookbookViewModel, navController)
    }
}

