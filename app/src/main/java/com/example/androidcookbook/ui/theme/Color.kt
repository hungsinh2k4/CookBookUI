package com.example.androidcookbook.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object Dark {
//    val Primary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.1f)
//    val PrimaryContainer = Color.hsl(175f, 0.49f, 0.9f)
//
//    val Secondary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f)
//    val OnSecondary = Color.hsl(207f, 0.48f, 0.82f)
//
//    val Tertiary = Color.hsl(hue = 178f, saturation = 0.49f, lightness = 0.1f)
//    val Scrim = Color.hsl(hue = 147f, saturation = 0.8f, lightness = 0.2f)

    val Primary = Color(0xFF333A47)
    val PrimaryContainer = Color(0xFFE8DEF8)
    val Secondary = Color(0xFFE8DEF8)
    val OnSecondary = Color(0xFFD7C2EF)
    val Tertiary = Color(0xFF323B44)
    val Scrim = Color(0xFF495463)

}

object Light {
//    val Primary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f)
//    val PrimaryContainer = Color.hsl(206f, 0.49f, 0.1f)
//
//    val Secondary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.1f)
//    val OnSecondary = Color.hsl(207f, 0.48f, 0.18f)
//
//    val Tertiary = Color.hsl(hue = 326f, saturation = 0.49f, lightness = 0.9f)
//    val Scrim = Color.hsl(hue = 147f, saturation = 0.8f, lightness = 0.8f) // this is accent

    val Primary = Color(0xFFFFFFFF)
    val PrimaryContainer = Color(0xFF333A47)
    val Secondary = Color(0xFF333A47)
    val OnSecondary = Color(0xFF4F4659)
    val Tertiary = Color(0xFFE8DEF8)
    val Scrim = Color(0xFFD7C2EF)
}

@Composable
fun transparentTextFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    errorContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
)

@Composable
fun transparentButtonColors() = ButtonColors(
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.primary,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
)