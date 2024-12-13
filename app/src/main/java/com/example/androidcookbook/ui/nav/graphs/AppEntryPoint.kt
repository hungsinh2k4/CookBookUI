package com.example.androidcookbook.ui.nav.graphs


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.nav.Routes

@Composable
fun AppEntryPoint(
    CookbookViewModel: CookbookViewModel = hiltViewModel<CookbookViewModel>(),
    navController: NavController
) {
    val isLoggedIn by CookbookViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(Routes.App.Newsfeed) {
                popUpTo("check_auth") {
                    inclusive = true
                }
            }


        } else {
            navController.navigate(Routes.Auth) {
                popUpTo("check_auth") {
                    inclusive = true
                }
            }
        }
    }
}
