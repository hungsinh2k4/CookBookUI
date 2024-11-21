package com.example.androidcookbook.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R
import com.example.androidcookbook.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookbookAppBar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onCreatePostClick: () -> Unit = {},
    searchButtonAction: () -> Unit = {},
    onMenuButtonClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF7F5346),
            scrolledContainerColor = Color(0xFF7F5346)
        ),
        title = {
            Text(
                text = "Cookbook",
                style = Typography.titleLarge
            )
        },
        actions = {
            Box(modifier = Modifier.padding(end = 6.dp)) {
                IconButton(
                    onClick = onCreatePostClick,
                    modifier = Modifier.size(30.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFFE8E8E8)
                    )
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.plus),
                        contentDescription = "Create Post",
                        contentScale = ContentScale.Crop

                    )
                }
            }

            Box(modifier = Modifier.padding(end = 6.dp)) {
                IconButton(
                    onClick = searchButtonAction,
                    modifier = Modifier.size(36.dp),
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.search_interface_symbol),
                        contentDescription = "Search",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Box(modifier = Modifier.padding(end = 6.dp)) {
                IconButton(
                    onClick = onMenuButtonClick,
                    modifier = Modifier.size(30.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFFE8E8E8)
                    )
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.hamburger),
                        contentDescription = "Menu Button",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarPreview() {
    CookbookAppBar()
}