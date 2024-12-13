package com.example.androidcookbook.ui.features.auth.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.features.auth.theme.SignLayoutTheme

@Composable
fun SignLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
        SignLayoutTheme {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceContainerHigh,
                                    MaterialTheme.colorScheme.surfaceContainerLow,
                                    MaterialTheme.colorScheme.surfaceContainerLowest
                                )
                            )
                        )
                        .requiredHeight(
                            LocalConfiguration.current.screenHeightDp.dp.times(1.01F)
                        )
                        .fillMaxSize()


                ) {
                        val ovalColor = MaterialTheme.colorScheme.onBackground
                        // Background and layout setup
                        val configuration = LocalConfiguration.current
                        val ovalWidth = configuration.screenWidthDp.dp.times(1.25f)
                        val ovalHeight = configuration.screenHeightDp.dp.times(0.25f)

                        Column(
                            modifier = Modifier
                                .wrapContentSize(
                                    unbounded = true
                                )
                                .offset(y = -(50).dp)
                        ) {

                            Box(
                                modifier = Modifier
                            ) {
                                Canvas(
                                    modifier = Modifier
                                        .height(ovalHeight)
                                        .requiredWidth(ovalWidth)

                                ) {
                                    drawOval(
                                        color = ovalColor,
                                    )
                                }
                                AppLogo(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .offset(y = (120).dp)

                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(11.dp)
                                .offset(y = (60).dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            content()
                        }
                    }

        }
}

@Preview
@Composable
fun LayoutPreview() {
    SignLayout {  }
}