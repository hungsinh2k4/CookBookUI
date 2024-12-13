package com.example.androidcookbook.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidcookbook.data.providers.ThemeType
import com.example.androidcookbook.ui.CookbookViewModel

val WHITE = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
//    primary = Dark.Primary,
//    secondary = Dark.Secondary,
//    tertiary = Dark.Tertiary,
//    scrim = Dark.Scrim, // this is accent
//    primaryContainer = Dark.PrimaryContainer,
////    primaryContainer = Color.Transparent,
//    onSecondary = Dark.OnSecondary,

    primary = WHITE
)

private val LightColorScheme = lightColorScheme(
//    primary = Light.Primary,
//    secondary = Light.Secondary,
//    tertiary = Light.Tertiary,
//    scrim = Light.Scrim, // this is accent
////    primaryContainer = Light.PrimaryContainer,
//    primaryContainer = Color.Transparent,
//    onSecondary = Light.OnSecondary,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
//    */
    primary = Color.Black,
    surface = WHITE,
    background = WHITE,
    scrim = Color.Transparent,
)

@Composable
fun AndroidCookbookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val viewModel = hiltViewModel<CookbookViewModel>()
    val theme = viewModel.themeType.collectAsState().value

    val colorScheme = when(theme) {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
        ThemeType.Default -> {
            if (darkTheme) DarkColorScheme
            else LightColorScheme
        }
        ThemeType.Light -> LightColorScheme
        ThemeType.Dark -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}