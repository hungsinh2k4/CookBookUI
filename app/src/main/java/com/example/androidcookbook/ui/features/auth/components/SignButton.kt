package com.example.androidcookbook.ui.features.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material.icons.outlined.Loop
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.ui.features.auth.theme.SignLayoutTheme
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun SignButton(
    onClick: () -> Unit,
    enabled: Boolean,
    actionText: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp),
        colors = if (enabled) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        else ButtonDefaults.buttonColors(),
        shape = RoundedCornerShape(size = 12.dp)
    ) {
        Row {
            Text(
                text = actionText,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                )
            )
            if (enabled) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    tint = Color.White,
                    contentDescription = null
                )
            } else {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    tint = Color.LightGray,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignButtonPreview() {
    SignLayoutTheme {
        SignButton({}, false,"Sign In")
    }
}