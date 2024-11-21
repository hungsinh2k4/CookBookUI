package com.example.androidcookbook.ui.screen

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
fun SearchScreen(result: String,modifier: Modifier = Modifier) {
    Text(text = result)
}

