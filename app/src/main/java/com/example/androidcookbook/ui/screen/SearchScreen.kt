package com.example.androidcookbook.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidcookbook.ui.component.CookbookAppBar
import com.example.androidcookbook.ui.component.SearchBar

@Composable
fun SearchScreen(
    result: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler { onBackButtonClick() }
    Text(text = result)
}

