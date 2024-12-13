package com.example.androidcookbook.ui.components.aigen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.R


@Composable
fun LoadingTextWithEllipsis() {
    val transition = rememberInfiniteTransition()

    // Animating opacity for each dot
    val dot1Alpha = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val dot2Alpha = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val dot3Alpha = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Compose the animated ellipsis with fading dots
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = "Good things take time",
            fontFamily = FontFamily(Font(R.font.playfairdisplay_regular)),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = ".",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.alpha(dot1Alpha.value)
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = ".",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.alpha(dot2Alpha.value)
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = ".",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.alpha(dot3Alpha.value)
        )
    }
}