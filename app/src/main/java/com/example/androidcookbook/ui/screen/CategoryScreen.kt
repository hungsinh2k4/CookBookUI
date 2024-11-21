package com.example.androidcookbook.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@Composable
fun CategoryScreen(modifier:Modifier = Modifier,categoryUiState: CategoryUiState) {
    when (categoryUiState) {
        is CategoryUiState.Loading -> Text("Loading")
        is CategoryUiState.Success ->
        {
            CategoryListScreen(
                categories = categoryUiState.categories,
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )
        }
        else -> Text("Error")
    }
}

@Composable
fun CategoryCard(category: Category, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Category",
                modifier = Modifier
                    .padding(16.dp),
                style = Typography.titleLarge,
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
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = category.strCategoryDescription,
                style = Typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun CategoryListScreen(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = categories,
            key = { category ->
                category.idCategory
            }
        ) {  category ->
            CategoryCard(category = category, modifier = Modifier.fillMaxSize())
        }
    }
}
