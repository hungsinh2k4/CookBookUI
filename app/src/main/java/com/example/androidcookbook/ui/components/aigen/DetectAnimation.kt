package com.example.androidcookbook.ui.components.aigen

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.androidcookbook.R


@Composable
fun DetectAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.image_detect))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(200.dp)
    )
}