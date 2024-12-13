package com.example.androidcookbook.ui.common.appbars

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.androidcookbook.ui.theme.Typography

object Dark {
    val Primary = Color(0xFF333A47)
    val PrimaryContainer = Color(0xFFE8DEF8)
    val Secondary = Color(0xFFE8DEF8)
    val OnSecondary = Color(0xFFD7C2EF)
    val Tertiary = Color(0xFF323B44)
    val Scrim = Color(0xFF495463)

}

object Light {
    val Primary = Color(0xFFE8DEF8)
    val PrimaryContainer = Color(0xFF333A47)
    val Secondary = Color(0xFF333A47)
    val OnSecondary = Color(0xFF4F4659)
    val Tertiary = Color(0xFFE8DEF8)
    val Scrim = Color(0xFFD7C2EF)
}

private val DarkColorScheme = darkColorScheme(
    primary = Dark.Primary,
    secondary = Dark.Secondary,
    tertiary = Dark.Tertiary,
    scrim = Dark.Scrim, // this is accent
    primaryContainer = Dark.PrimaryContainer,
//    primaryContainer = Color.Transparent,
    onSecondary = Dark.OnSecondary,

)

private val LightColorScheme = lightColorScheme(
    primary = Light.Primary,
    secondary = Light.Secondary,
    tertiary = Light.Tertiary,
    scrim = Light.Scrim, // this is accent
    primaryContainer = Light.PrimaryContainer,
//    primaryContainer = Color.Transparent,
    onSecondary = Light.OnSecondary,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
    background = Color.White
)

@Composable
fun AppBarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

