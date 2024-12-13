package com.example.androidcookbook.ui.nav.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> sharedViewModel(
    backStackEntry: NavBackStackEntry,
    navController: NavController,
    route: Any,
): VM {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(route)
    }
    return hiltViewModel<VM>(parentEntry)
}

@Composable
inline fun <reified VM : ViewModel, reified VMF> sharedViewModel(
    backStackEntry: NavBackStackEntry,
    navController: NavController,
    route: Any,
    noinline creationCallback: (VMF) -> VM
): VM {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(route)
    }
    return hiltViewModel<VM, VMF>(
        viewModelStoreOwner = parentEntry,
        creationCallback = creationCallback
    )
}