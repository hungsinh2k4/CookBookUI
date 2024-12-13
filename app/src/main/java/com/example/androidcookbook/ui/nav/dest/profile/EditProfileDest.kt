package com.example.androidcookbook.ui.nav.dest.profile

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.features.userprofile.edit.EditProfileScreen
import com.example.androidcookbook.ui.features.userprofile.edit.EditProfileViewModel
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import kotlin.reflect.typeOf

fun NavGraphBuilder.editProfile(
    viewModel: CookbookViewModel,
    navController: NavHostController,
) {
    composable<Routes.EditProfile>(
        typeMap = mapOf(
            typeOf<User>() to CustomNavTypes.UserType
        )
    ){
        viewModel.updateTopBarState(CookbookUiState.TopBarState.NoTopBar)
        viewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
        viewModel.updateCanNavigateBack(true)

        val user = it.toRoute<Routes.EditProfile>().user

        val editProfileViewModel = hiltViewModel<EditProfileViewModel, EditProfileViewModel.EditProfileViewModelFactory>{ factory ->
            factory.create(user)
        }

        EditProfileScreen(
            avatarPath = editProfileViewModel.avatarUri.collectAsState().value,
            bannerPath = editProfileViewModel.bannerUri.collectAsState().value,
            bio = editProfileViewModel.bio.collectAsState().value,
            name = editProfileViewModel.name.collectAsState().value,
            updateAvatarUri = { uri -> editProfileViewModel.updateAvatarUri(uri) },
            updateBannerUri = { uri -> editProfileViewModel.updateBannerUri(uri) },
            onBioChange = { bio -> editProfileViewModel.updateBio(bio) },
            onNameChange = { name -> editProfileViewModel.updateName(name) },
            onUpdate = { editProfileViewModel.sendUpdateUserRequest(
                onSuccessNavigate = {
                    navController.navigateUp()
                }
            ) },
            onBackButtonClick = {
                navController.navigateUp()
            },
        )
    }
}