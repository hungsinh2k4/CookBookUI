package com.example.androidcookbook.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R
import com.example.androidcookbook.model.Category
import com.example.androidcookbook.ui.theme.Typography
import com.example.androidcookbook.ui.uistate.CategoryUiState
import kotlinx.coroutines.delay

@Composable
fun CategoryScreen(modifier: Modifier = Modifier, categoryUiState: CategoryUiState) {
    when (categoryUiState) {
        is CategoryUiState.Loading -> Text("Loading")
        is CategoryUiState.Success -> {
            CategoryListScreen(
                categories = categoryUiState.categories,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 16.dp, end = 8.dp)
            )
        }

        else -> Text("Error")
    }
}

@Composable
fun RandomMeal(modifier: Modifier = Modifier, randomMeals: List<Category>) {
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            val nextIndex = (listState.firstVisibleItemIndex + 1) % randomMeals.size
            listState.animateScrollToItem(index = nextIndex)
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        items(items = randomMeals) { randomMeal ->
            Box(
                modifier = Modifier
                    .width(300.dp),
                contentAlignment = Alignment.Center
            ) {
                CategoryCard(
                    category = randomMeal,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color(0xFFE8D6D2))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = category.strCategory,
                modifier = Modifier
                    .padding(16.dp),
                style = Typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(category.strCategoryThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
        }
    }
}

@Composable
private fun CategoryListScreen(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp)
) {
    // TODO: Nesting a LazyRow inside of a LazyVerticalGrid seems incorrect,
    //  should find a different solution.
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxCurrentLineSpan) },) {
            RandomMeal(randomMeals = categories)
        }
        item {
            Text(
                text = "Category",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        item { }
        items(
            items = categories,
        ) { category ->
            CategoryCard(category = category, modifier = Modifier.fillMaxSize())
        }
    }
}

