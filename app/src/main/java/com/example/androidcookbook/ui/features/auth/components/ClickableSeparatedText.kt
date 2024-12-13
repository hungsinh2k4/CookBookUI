package com.example.androidcookbook.ui.features.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ClickableSeparatedText(
    unclickableText: String,
    clickableText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        Text(
            text = unclickableText,
            color = MaterialTheme.colorScheme.inversePrimary,
            fontWeight = FontWeight(600)
        )
        ClickableText(
            clickableText = clickableText,
            onClick = onClick,
        )
    }
}

@Composable
fun ClickableText(
    clickableText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier.clickable {
            onClick()
        },
        text = clickableText,
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight(600)
    )
}

@Preview
@Composable
fun TextPreview() {
    ClickableSeparatedText(
        unclickableText = "Doesn’t have account ? ",
        clickableText = "Sign Up",
        {}
    )
}