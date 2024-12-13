package com.example.androidcookbook.ui.nav.dest.post

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.common.screens.GuestLoginScreen
import com.example.androidcookbook.ui.features.post.create.AddIngredientDialog
import com.example.androidcookbook.ui.features.post.create.AddStepDialog
import com.example.androidcookbook.ui.features.post.create.CreatePostScreen
import com.example.androidcookbook.ui.features.post.create.CreatePostType
import com.example.androidcookbook.ui.features.post.create.CreatePostViewModel
import com.example.androidcookbook.ui.features.post.create.UpdateIngredientDialog
import com.example.androidcookbook.ui.features.post.create.UpdateIngredientDialogState
import com.example.androidcookbook.ui.features.post.create.UpdateStepDialog
import com.example.androidcookbook.ui.features.post.create.UpdateStepDialogState
import com.example.androidcookbook.ui.nav.CustomNavTypes
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.guestNavToAuth
import kotlin.reflect.typeOf

fun NavGraphBuilder.createPost(
    viewModel: CookbookViewModel,
    currentUser: User,
    navController: NavHostController,
) {
    composable<Routes.CreatePost>(
        typeMap = mapOf(
            typeOf<Post>() to CustomNavTypes.PostType,
        )
    ) {
        viewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
        viewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
        viewModel.updateCanNavigateBack(true)

        if (currentUser.id == GUEST_ID) {
            GuestLoginScreen {
                navController.guestNavToAuth()
            }
            return@composable
        }

        val createPostViewModel = hiltViewModel<CreatePostViewModel, CreatePostViewModel.CreatePostViewModelFactory>{ factory ->
            factory.create(Post())
        }

        val postTitle by createPostViewModel.postTitle.collectAsState()
        val postBody by createPostViewModel.postBody.collectAsState()
        val postImageUri by createPostViewModel.postImageUri.collectAsState()

        CreatePostScreen(
            author = currentUser,
            postTitle = postTitle,
            updatePostTitle = {
                createPostViewModel.updatePostTitle(it)
            },
            postBody = postBody,
            updatePostBody = {
                createPostViewModel.updatePostBody(it)
            },
            recipe = createPostViewModel.recipe.collectAsState().value,
            onAddNewStep = {
                createPostViewModel.openAddStepDialog()
            },
            updateStep = {
                createPostViewModel.openUpdateStep(it)
            },
            deleteStep = {
                createPostViewModel.deleteStep(it)
            },
            ingredients = createPostViewModel.ingredients.collectAsState().value,
            onAddNewIngredient = {
                createPostViewModel.openAddIngredientDialog()
            },
            updateIngredient = {
                createPostViewModel.openUpdateIngredient(it)
            },
            deleteIngredient = {
                createPostViewModel.deleteIngredient(it)
            },
            postImageUri = postImageUri,
            updatePostImageUri = {
                createPostViewModel.updatePostImageUri(it)
            },
            onPostButtonClick = {
                createPostViewModel.createPost(
                    onSuccessNavigate = { post ->
                        navController.navigate(Routes.App.PostDetails(post.id)) {
                            popUpTo<Routes.CreatePost> {
                                inclusive = true
                            }
                        }
                    }
                )
            },
            onBackButtonClick = {
                navController.navigateUp()
            },
            cookTime = createPostViewModel.cookTime.collectAsState().value,
            onCookTimeChange = {
                createPostViewModel.updateCookTime(it)
            },
            createType = CreatePostType.Post,
            onUserClick = { user ->
                navController.navigate(Routes.App.UserProfile(user.id))
            },
            showErrorMessage = createPostViewModel.showErrorMessage.collectAsState().value,
        )
        if (createPostViewModel.isAddStepDialogOpen.collectAsState().value) {
            AddStepDialog(
                onAddStep = {
                    createPostViewModel.addStepToRecipe(it)
                    createPostViewModel.closeDialog()
                },
                onDismissRequest = {
                    createPostViewModel.closeDialog()
                }
            )
        }
        if (createPostViewModel.isAddIngredientDialogOpen.collectAsState().value) {
            AddIngredientDialog(
                onAddIngredient = {
                    createPostViewModel.addIngredient(it)
                    createPostViewModel.closeDialog()
                },
                onDismissRequest = {
                    createPostViewModel.closeDialog()
                }
            )
        }
        when (val updateStepDialogState =
            createPostViewModel.updateStepDialogState.collectAsState().value) {
            is UpdateStepDialogState.Open -> {
                UpdateStepDialog(
                    step = updateStepDialogState.step,
                    onUpdateStep = {
                        createPostViewModel.updateStep(updateStepDialogState.index, it)
                        createPostViewModel.closeDialog()
                    },
                    onDismissRequest = {
                        createPostViewModel.closeDialog()
                    }
                )
            }

            UpdateStepDialogState.Closed -> {}
        }
        when (val updateIngredientDialogState =
            createPostViewModel.updateIngredientDialogState.collectAsState().value) {
            is UpdateIngredientDialogState.Open -> {
                UpdateIngredientDialog(
                    ingredient = updateIngredientDialogState.ingredient,
                    onUpdateIngredient = {
                        createPostViewModel.updateIngredient(updateIngredientDialogState.index, it)
                        createPostViewModel.closeDialog()
                    },
                    onDismissRequest = {
                        createPostViewModel.closeDialog()
                    }
                )
            }

            UpdateIngredientDialogState.Closed -> {}
        }
    }
}

