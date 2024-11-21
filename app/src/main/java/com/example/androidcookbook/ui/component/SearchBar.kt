package com.example.androidcookbook.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onValueChange: (String) -> Unit,navigateBackAction: () -> Unit, searchQuery: String, modifier: Modifier = Modifier) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF7F5346)),
        title = {
            Box(modifier = Modifier.height(48.dp)) {
                TextField(
                    modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
                    value = searchQuery,
                    onValueChange = onValueChange,
                    placeholder = { Text("Search..", style = Typography.labelMedium) },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        textColor = Color.White
                    ),
                    singleLine = true
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = navigateBackAction) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.White
                )
            }
        },
    )
}