package com.example.androidcookbook.ui.features.category

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R
import com.example.androidcookbook.data.providers.ThemeType
import com.example.androidcookbook.domain.model.category.Category
import com.example.androidcookbook.domain.model.recipe.DisplayRecipeDetail
import com.example.androidcookbook.domain.model.recipe.Recipe
import com.example.androidcookbook.domain.model.recipe.RecipeList
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.common.appbars.AppBarTheme
import com.example.androidcookbook.ui.common.containers.RefreshableScreen
import com.example.androidcookbook.ui.components.SimpleNavigateUpTopBar
import com.example.androidcookbook.ui.components.category.CategoryListScreenLoading
import com.example.androidcookbook.ui.components.category.CategoryScreenLoading
import com.example.androidcookbook.ui.components.category.RecipeDetailScreenLoading
import com.example.androidcookbook.ui.theme.Typography

val thumbsMap = mapOf(
    "Beef" to R.drawable.beef,
    "Chicken" to R.drawable.chicken,
    "Dessert" to R.drawable.dessert,
    "Lamb" to R.drawable.lamb,
    "Miscellaneous" to R.drawable.miscellaneous,
    "Pasta" to R.drawable.pasta,
    "Seafood" to R.drawable.seafood,
    "Pork" to R.drawable.pork,
    "Side" to R.drawable.side,
    "Starter" to R.drawable.starter,
    "Vegan" to R.drawable.vegan,
    "Vegetarian" to R.drawable.vegetarian,
    "Breakfast" to R.drawable.breakfast,
    "Goat" to R.drawable.goat,
)


const val CATEGORY_SCREEN_TAG = "CategoryScreen"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel,
    cookbookViewModel: CookbookViewModel,
) {
    val navController = rememberNavController()
    val categoryUiState = categoryViewModel.categoryUiState.collectAsState()

    var currentDestination = "categoryList"

    val darkTheme = when(cookbookViewModel.themeType.collectAsState().value) {
        ThemeType.Default -> isSystemInDarkTheme()
        ThemeType.Dark -> true
        ThemeType.Light -> false
    }


    NavHost(
        navController = navController,
        startDestination = currentDestination
    ) {

        composable("categoryList") {
            RefreshableScreen(
                isRefreshing = categoryViewModel.isRefreshing.collectAsState().value,
                onRefresh = { categoryViewModel.refresh() }
            ) {
                if (categoryUiState.value.isLoading) {
                    cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
                    cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
                    cookbookViewModel.updateCanNavigateBack(false)
                    CategoryScreenLoading()
                } else if (categoryUiState.value.isCategory) {
                    CategoryListScreen(
                        categories = categoryViewModel.categories,
                        randomMeals = categoryViewModel.randomMeals,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                        onCategoryClick = { category ->
                            navController.navigate("categoryDetail")
                            categoryViewModel.getListDetail(category)
                            categoryViewModel.setTopBarState(false)

                            cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
                        },
                        onRandomMealClick = {
                            id ->
                            categoryViewModel.getRecipesDetails(id)
                            cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
                            categoryViewModel.setTopBarState(false)
                            navController.navigate("randomRecipeDetail")

                        }
                    )
                }
            }
        }

        composable("categoryDetail") {

            val navigationBack = {
                categoryViewModel.updateUiStateCategory()
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
                categoryViewModel.setTopBarState(true)
                cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
                categoryViewModel.recipeList = RecipeList(mutableListOf())
                navController.navigate("categoryList")
            }
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            if (!categoryViewModel.isTopBarSet.collectAsState().value) {
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {
                    AppBarTheme(darkTheme) {
                        SimpleNavigateUpTopBar(
                            navigateBackAction = {
                                navigationBack()
                            },
                            title = categoryViewModel.currentCategory,
                            scrollBehavior = scrollBehavior
                        )

                    }
                })
            }

            if (categoryUiState.value.isLoadingRecipeList) {
                CategoryListScreenLoading(navigationBack)
            } else if (categoryUiState.value.isListDetail) {

                CategoryDetailListScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp)
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    listDetail = categoryViewModel.recipeList,
                    navigationBack = navigationBack,
                    onClick = { id ->
                        categoryViewModel.getRecipesDetails(id)
                        categoryViewModel.setTopBarState(false)
                        navController.navigate("recipeDetail")

                    }

                )
            }
        }

        composable("recipeDetail") {

            if (!categoryViewModel.isTopBarSet.collectAsState().value) {
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {

                })
            }
            if (categoryUiState.value.isLoadingRecipeDetails) {
                RecipeDetailScreenLoading(navigateBack = {

                    categoryViewModel.updateUiStateCategoryDetail()
                    categoryViewModel.setTopBarState(false)
                    categoryViewModel.currentRecipeDetail = DisplayRecipeDetail()
                    navController.navigate("categoryDetail")

                })
            } else if (categoryUiState.value.isRecipeDetail) {
                CategoryDetail(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    recipeDetail = categoryViewModel.currentRecipeDetail,
                    navigateBackAction = {

                        categoryViewModel.updateUiStateCategoryDetail()
                        categoryViewModel.setTopBarState(false)
                        categoryViewModel.currentRecipeDetail = DisplayRecipeDetail()
                        navController.navigate("categoryDetail")
                    },
                )
            }
        }

        composable("randomRecipeDetail") {

            if (!categoryViewModel.isTopBarSet.collectAsState().value) {
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {

                })
            }
            if (categoryUiState.value.isLoadingRecipeDetails) {
                RecipeDetailScreenLoading(navigateBack = {
                    cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
                    cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
                    categoryViewModel.updateUiStateCategory()
                    categoryViewModel.setTopBarState(true)
                    categoryViewModel.currentRecipeDetail = DisplayRecipeDetail()
                    navController.navigate("categoryList")

                })
            } else if (categoryUiState.value.isRecipeDetail) {
                CategoryDetail(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                    recipeDetail = categoryViewModel.currentRecipeDetail,
                    navigateBackAction = {
                        cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
                        cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
                        categoryViewModel.updateUiStateCategory()
                        categoryViewModel.setTopBarState(true)
                        categoryViewModel.currentRecipeDetail = DisplayRecipeDetail()
                        navController.navigate("categoryList")
                    },
                )
            }
        }



        composable("error") {
            Text("Error")
        }
    }
}


@Composable
fun CategoryCard(category: Category, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Box {


            thumbsMap[category.strCategory]?.let { painterResource(it) }?.let {
                Image(
                    painter = it,
                    contentDescription = category.strCategory,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            onClick(category.strCategory)
                        }
                )
            }

            // Text Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = category.strCategory,
                    style = Typography.titleSmall.copy(color = Color.White),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}


@Composable
fun RandomMealCard(randomMeal: Recipe, modifier: Modifier = Modifier,onClick: (Int) -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
//        border = BorderStroke(2.dp, Color(0xFFE8D6D2))
    ) {
        Box {
            // Image Background
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp).clickable {
                        onClick(randomMeal.idMeal)
                    }, // Adjust height as needed
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(randomMeal.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )

            // Text Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = randomMeal.strMeal,
                    style = Typography.titleSmall.copy(color = Color.White),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoryListScreen(
    categories: List<Category>,
    randomMeals: List<Recipe>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    onCategoryClick: (String) -> Unit,
    onRandomMealClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            // Full-width Text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) // Optional padding
            ) {
                Text(
                    text = "Discover new dishes",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.CenterStart) // Align text if needed
                )
            }
        }

        items(items = randomMeals) { randomMeal ->
            RandomMealCard(randomMeal = randomMeal, Modifier.fillMaxSize(),onClick = onRandomMealClick)

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
            CategoryCard(
                category = category,
                modifier = Modifier.fillMaxSize(),
                onClick = onCategoryClick
            )
        }
    }
}

