package com.example.androidcookbook.ui.features.aigen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.androidcookbook.ui.theme.Light
import com.example.androidcookbook.ui.theme.Typography


private val AiLight = lightColorScheme(
    surfaceContainer = Color.hsl(26f,0.49f,0.90f),
    outlineVariant = Color.Black,
    outline = Color.hsl(180f,0.29f,0.07f),
    primary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.1f),
    secondary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f),
    error = Color.hsl(0f,0.8f,0.4f)
)

//val DarkSurfaceContainer = Color.hsl(26f,0.49f,0.90f)
val DarkSurfaceContainer = Color(0xFFCAC2CD)


private val AiDark = darkColorScheme(
//    surfaceContainer =  Color.hsl(180f,0.29f,0.07f),
//    primary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f),
//    secondary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.1f),
//    outline = Color.hsl(180f,0.29f,0.93f),
//    error = Color.hsl(0f,0.8f,0.8f)
    surfaceContainer = Color.LightGray,
    outlineVariant = Color.hsl(206f,0.49f,0.90f),
    outline = Color.hsl(180f,0.29f,0.07f),
    primary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.1f),
    secondary = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f),
    error = Color.hsl(0f,0.8f,0.4f)
)

@Composable
fun AiScreenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> AiDark
        else -> AiLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}