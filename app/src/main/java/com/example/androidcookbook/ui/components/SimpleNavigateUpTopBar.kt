package com.example.androidcookbook.ui.components

import android.util.Log
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.androidcookbook.ui.common.appbars.defaultTopAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleNavigateUpTopBar(modifier: Modifier = Modifier,navigateBackAction: () -> Unit,title: String,scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        colors = defaultTopAppBarColor(),
        title = {
            Text(
                text =title,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateBackAction) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        , scrollBehavior = scrollBehavior
    )
}