package com.example.androidcookbook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.*
import com.example.androidcookbook.R


@Composable
fun ComponentLoadingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.component_skeleton_loading))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun AsyncImageWithLottie(
    imageUrl: String,
    modifier: Modifier = Modifier,
    lottieAnimation: Int, // Lottie animation resource ID
) {
    var imageLoading by remember { mutableStateOf(true) }
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieAnimation))
    val lottieAnimationState = rememberLottieAnimatable()

    LaunchedEffect(lottieComposition) {
        lottieAnimationState.animate(composition = lottieComposition)
    }

    Box(modifier = modifier) {
        if (imageLoading) {
            LottieAnimation(
                composition = lottieComposition,
                progress = lottieAnimationState.progress,
                modifier = Modifier.fillMaxSize()
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            onSuccess = { imageLoading = false },
            onError = { imageLoading = false }
        )

    }
}