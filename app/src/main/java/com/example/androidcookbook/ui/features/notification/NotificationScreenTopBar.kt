package com.example.androidcookbook.ui.features.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidcookbook.ui.common.appbars.AppBarTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotificationScreenTopBar(
    onBackButtonClick: () -> Unit,
    onClearAllClick: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.secondary,
                navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                actionIconContentColor = MaterialTheme.colorScheme.secondary
            ),
            title = {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
//                        fontSize = MaterialTheme.typography.titleMedium.fontSize.times(1.25f),
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                }
            },
            actions = {
                IconButton(onClick = onClearAllClick) {
                    Icon(
                        imageVector = Icons.Default.ClearAll,
                        contentDescription = "Clear all",
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        )
        HorizontalDivider()
    }
}

@Composable
@Preview(showBackground = true)
fun NotificationScreenTopBarPreview() {
    AppBarTheme {
        NotificationScreenTopBar(
            onBackButtonClick = {},
            onClearAllClick = {}
        )
    }
}

@Composable
@Preview
fun NotificationScreenTopBarDarkPreview() {
    AppBarTheme(darkTheme = true) {
        NotificationScreenTopBar(
            onBackButtonClick = {},
            onClearAllClick = {}
        )
    }
}