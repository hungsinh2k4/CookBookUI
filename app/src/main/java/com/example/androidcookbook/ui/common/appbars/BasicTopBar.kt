package com.example.androidcookbook.ui.common.appbars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidcookbook.ui.common.iconbuttons.BackButton

@Composable
fun BasicTopBar(
    onBackButtonClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onBackButtonClick)
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}