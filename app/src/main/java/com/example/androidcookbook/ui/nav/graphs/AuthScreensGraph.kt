package com.example.androidcookbook.ui.nav.graphs

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.example.androidcookbook.ui.common.dialog.MinimalDialog
import com.example.androidcookbook.ui.features.auth.AuthViewModel
import com.example.androidcookbook.ui.features.auth.screens.ForgotPasswordScreen
import com.example.androidcookbook.ui.features.auth.screens.LoginScreen
import com.example.androidcookbook.ui.features.auth.screens.RegisterScreen
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.getViewModel

/**
 * Login, registration, forgot password screens nav graph builder
 * (Unauthenticated user)
 */
fun NavGraphBuilder.authScreens(navController: NavController, updateAppBar: () -> Unit) {
    navigation<Routes.Auth>(
        startDestination = Routes.Auth.Login
    ) {
        // Scope the ViewModel to the navigation graph
        composable<Routes.Auth.Login> {
            updateAppBar()
            val authViewModel: AuthViewModel = getViewModel(it, navController, Routes.Auth)
            Log.d("Login", authViewModel.toString())
            LoginScreen(
                onForgotPasswordClick = {
                    // TODO: navigate to ForgotPassword
                },
                onNavigateToSignUp = {
                    navController.navigate(Routes.Auth.Register)
                },
                onSignInClick = { username, password ->
                    authViewModel.signIn(username, password) {
                        navController.navigate(Routes.DialogDestination)
                    }
                },
                onUseAsGuest = {
                    navController.navigate(Routes.App)
                }
            )
        }
        composable<Routes.Auth.Register> {
            updateAppBar()
            val authViewModel: AuthViewModel = getViewModel(it, navController, Routes.Auth)
            Log.d("Login", authViewModel.toString())
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToSignIn = {
                    navController.navigate(Routes.Auth.Login)
                },
            )
        }
        composable<Routes.Auth.ForgotPassword> {
            updateAppBar()
            val authViewModel: AuthViewModel = getViewModel(it, navController, Routes.Auth)
            Log.d("Login", authViewModel.toString())
            ForgotPasswordScreen(
                // TODO
            )
        }
        dialog<Routes.DialogDestination> {
            val authViewModel: AuthViewModel = getViewModel(it, navController, Routes.Auth)
            Log.d("Login", authViewModel.toString())
            val authUiState by authViewModel.uiState.collectAsState()
            MinimalDialog(
                dialogMessage = authUiState.dialogMessage,
                onDismissRequest = {
                    authViewModel.changeOpenDialog(false)
                    navController.popBackStack()

                    if (authUiState.signInSuccess) {
                        navController.navigate(Routes.App) {
                            // Clear authScreens from the backstack
                            popUpTo<Routes.Auth> {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
    }
}

