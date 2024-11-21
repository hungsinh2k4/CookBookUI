package com.example.androidcookbook.ui.features.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun SignButton(
    onClick: () -> Unit,
    actionText: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF916246)),
        shape = RoundedCornerShape(size = 30.dp)
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
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun SignButtonPreview() {
    AndroidCookbookTheme {
        SignButton({}, "Sign In")
    }
}