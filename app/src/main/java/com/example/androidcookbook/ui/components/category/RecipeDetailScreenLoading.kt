package com.example.androidcookbook.ui.components.category


import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R
import com.example.androidcookbook.domain.model.recipe.DisplayRecipeDetail
import com.example.androidcookbook.ui.components.AppLoadingAnimation
import com.example.androidcookbook.ui.components.ComponentLoadingAnimation
import com.example.androidcookbook.ui.components.aigen.DashedLine

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun RecipeDetailScreenLoading(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {

    BackHandler {
        navigateBack()
    }
    val scrollState = rememberLazyListState()

    // Check if LazyColumn is at the top
    // This will trigger every time the scroll position changes

    var currentImgHeight by remember { mutableStateOf(300f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                val isAtTop =
                    scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset == 0
                Log.d("test", isAtTop.toString())
                if (delta < 0 || (isAtTop)) {
                    val newImgHeight = currentImgHeight + delta
                    val previousImgSize = currentImgHeight
                    currentImgHeight = newImgHeight.coerceIn(75f, 300f)
                    val consumed = currentImgHeight - previousImgSize
                    return Offset(0f, consumed)
                } else {
                    return super.onPreScroll(available, source)
                }

            }
        }
    }

    Box(Modifier.nestedScroll(nestedScrollConnection)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(currentImgHeight.dp)
        ) {
            ComponentLoadingAnimation()

            IconButton(
                onClick = navigateBack,
                modifier = Modifier.align(Alignment.TopStart) // Align navigation icon to the top start
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .offset {
                    IntOffset(0, currentImgHeight.toInt() * 3)
                }, scrollState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item() {
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp
                )
            }

            item {
                Spacer(modifier = Modifier.size(40.dp))
            }

            item {
                Divider(color = MaterialTheme.colorScheme.onBackground)
            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
            }

            item {
                Text(
                    text =  " - ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
            }

            item {
                Divider(color = MaterialTheme.colorScheme.onBackground)
            }

            item {
                Spacer(Modifier.size(40.dp))
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Ingredients",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )
            }

            item {
                Spacer(Modifier.size(20.dp))
            }



            item {
                Spacer(Modifier.size(40.dp))
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Instruction",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )
            }

            item { Spacer(Modifier.size(20.dp)) }


            item {
                Spacer(Modifier.size(100.dp))
            }

        }

    }

}