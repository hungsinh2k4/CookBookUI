package com.example.androidcookbook.ui.features.auth.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.androidcookbook.ui.theme.Typography

val LightTitle = Color(52, 108, 151)
val LightText = Color(13, 27, 38)
val LightButtonColor = Color(64, 120, 170)
val LightDisabledButtonColor = Color(38, 91, 136, 255)
val LightClickableText = Color(48, 122, 43)
val LightBackground = Color(217, 231, 242)
val LightOval = Color(179, 207, 229)
val LightErrorText = Color(127, 60, 57)
val LightTextOutline = Color(169, 202, 228)
val LightGradientHighest = Color(255, 255, 255)
val LightGradientHigh = Color(217, 231, 242)
val LightGradientLow = Color(184, 211, 233)
val LightGradientLowest = Color(99, 161, 209)
val LightLogo = Color.White


val LightSignLayout = lightColorScheme(
    primary = LightTitle,
    inversePrimary = LightText,
    secondary = LightButtonColor,
    tertiary = LightClickableText,
    background = LightBackground,
    onBackground = LightOval,
    error = LightErrorText,
    outline = LightTextOutline,
    surfaceContainerHigh = LightGradientHigh,
    surfaceContainerHighest = LightGradientHighest,
    surfaceContainerLow = LightGradientLow,
    surfaceContainerLowest = LightGradientLowest,
    surface = LightLogo,
    surfaceDim = LightDisabledButtonColor,
)

val DarkTitle = Color(217, 231, 242)
val DarkText = Color(217, 231, 242)
val DarkButtonColor = Color(64, 120, 170)
val DarkDisabledButtonColor = Color(38, 91, 136, 255)
val DarkClickableText = Color(134, 147, 95)
val DarkBackground = Color(25, 87, 135)
val DarkOval = Color(26, 54, 76)
val DarkErrorText = Color(127, 60, 57)
val DarkTextOutline = Color(217, 231, 242)
val DarkGradientHighest = Color(25, 87, 135)
val DarkGradientHigh = Color(25, 87, 135)
val DarkGradientLow = Color(11, 28, 40)
val DarkGradientLowest = Color(11, 28, 40)
val DarkLogo = Color(17, 53, 81)

val DarkSignLayout = darkColorScheme(
    primary = DarkTitle,
    inversePrimary = DarkText,
    secondary = DarkButtonColor,
    tertiary = DarkClickableText,
    background = DarkBackground,
    onBackground = DarkOval,
    error = DarkErrorText,
    outline = DarkTextOutline,
    surfaceContainerHigh = DarkGradientHigh,
    surfaceContainerHighest = DarkGradientHighest,
    surfaceContainerLow = DarkGradientLow,
    surfaceContainerLowest = DarkGradientLowest,
    surface = DarkLogo,
    surfaceDim = DarkDisabledButtonColor
)

@Composable
fun SignLayoutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkSignLayout
        else -> LightSignLayout
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}