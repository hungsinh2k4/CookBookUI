package com.example.androidcookbook.ui.nav.utils

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.nav.Routes

/*
 * Extension function to tell the navController to navigate to the route only if the
 * currentDestination is not already on it.
 */
fun <T : Any> NavHostController.navigateIfNotOn(
    route: T,
    popBackStack: Boolean = false,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    if (currentDestination?.hasRoute(route) == true) {
        return
    }
    if (popBackStack) {
        popBackStack()
    }
    navigate(route) {
        builder()
    }
}

fun <T: Any> NavDestination.hasRoute(route: T) =
    hasRoute(route::class)

/*
 * Extension function to check whether the current destination's hierarchy contains the parent route
 */
fun <T: Any> NavDestination.hasParent(route: T): Boolean =
    hierarchy.any {
        it.hasRoute(route::class)
    }

/*
 * Extension function to navigate to the other profile screen.
 */
fun NavHostController.navigateToProfile(currentUser: User, targetUser: User) {
    if (currentUser.id == targetUser.id) {
        navigate(Routes.App.UserProfile(currentUser.id))
    } else {
        navigate(Routes.OtherProfile(targetUser.id))
    }
}

fun NavHostController.guestNavToAuth() {
    navigate(Routes.Auth) {
        popUpTo<Routes.App> {
            inclusive = true
        }
    }
}