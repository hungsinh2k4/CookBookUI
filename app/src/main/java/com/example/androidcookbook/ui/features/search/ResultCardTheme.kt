package com.example.androidcookbook.ui.features.search

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.androidcookbook.ui.theme.Typography

val ResultCardLightSurfaceContainer = Color(0xFFFFF7D1)
val ResultCardLightOutline = Color(0xFFFFD09B)

val ResultCardDarkSurfaceContainer = Color(0xFF7C7C7C)
val ResultCardDarkOutline = Color(0xFFFFFFFF)

private val ResultCardLight = lightColorScheme(
    surfaceContainer = ResultCardLightSurfaceContainer,
    outline = ResultCardLightOutline,
//    surfaceContainer = Color.hsl(26f, 0.49f, 0.9f),
//    outline = Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f)
)

private val ResultCardDark = darkColorScheme(
//    surfaceContainer = ResultCardDarkSurfaceContainer,
//    outline = ResultCardDarkOutline,
    surfaceContainer = Color.hsl(26f, 0.49f, 0.1f),
    outline =Color.hsl(hue = 207f, saturation = 0.5f, lightness = 0.9f)
)

@Composable
fun ResultCardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> ResultCardDark
        else -> ResultCardLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}