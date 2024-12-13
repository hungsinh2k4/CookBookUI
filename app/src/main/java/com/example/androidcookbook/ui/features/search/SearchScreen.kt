package com.example.androidcookbook.ui.features.search

import android.app.Application
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R
import com.example.androidcookbook.data.mocks.SamplePosts
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.recipe.DisplayRecipeDetail
import com.example.androidcookbook.domain.model.recipe.Recipe
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.common.screens.GuestLoginScreen
import com.example.androidcookbook.ui.common.screens.LoadingScreen
import com.example.androidcookbook.ui.features.category.CategoryDetail
import com.example.androidcookbook.ui.features.newsfeed.NewsfeedCard
import com.example.androidcookbook.ui.features.newsfeed.NewsfeedScreen
import com.example.androidcookbook.ui.features.post.details.PostDetailsScreen
import com.example.androidcookbook.ui.getDarkThemeConfig
import com.example.androidcookbook.ui.nav.utils.guestNavToAuth


@Composable
fun SearchScreen(
    currentUser: User,
    viewModel: SearchViewModel,
    searchUiState: SearchUiState,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSeeMoreClick: (User) -> Unit = {},
    onSeeDetailsClick:(Post) -> Unit = {},
    navController: NavHostController,
) {
    val pagerState = rememberPagerState(
        pageCount = { 4 }
    )
    BackHandler {
        when (searchUiState.currentScreen) {
            SearchScreenState.Food -> viewModel.ChangeScreenState(SearchScreenState.Waiting)
            SearchScreenState.Posts -> viewModel.ChangeScreenState(SearchScreenState.Food)
            SearchScreenState.Detail -> viewModel.ChangeScreenState(SearchScreenState.Food)
            SearchScreenState.Waiting -> onBackButtonClick()
        }
    }
    val loadRecipeSuccessful = viewModel.loadCurrentRecipeSuccessful.collectAsState().value
    if (searchUiState.fail) {
        Text(
            text = searchUiState.result,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    } else {
        when (searchUiState.currentScreen) {
            SearchScreenState.Waiting -> {

            }
            SearchScreenState.Food -> {
                Column {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier.weight(1f),
                        indicator = {
                            tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(
                                    currentTabPosition = tabPositions[pagerState.currentPage]
                                )
                            )
                        }
                    ) {
                        SearchTab.values().forEach {
                            Tab(
                                selected = pagerState.currentPage == it.ordinal,
                                onClick = {
                                    pagerState.requestScrollToPage(it.ordinal)
                                          },
                                text = { Text(text = it.name) }
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(12f),
                        verticalAlignment = Alignment.Top
                    ) {
                        var state = rememberLazyListState()
                        var isAtBottom = state.isAtBottom()
                        var userChecked by remember { mutableStateOf(true) }
                        LazyColumn(
                            state = state,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            when (it) {
                                0 -> {
                                    item {
                                        if (currentUser.id == GUEST_ID) {
                                            GuestLoginScreen {
                                                navController.guestNavToAuth()
                                            }
                                            return@item
                                        }
                                        SearchAllResultsScreen(
                                            posts = searchUiState.searchALlResults.posts,
                                            users = searchUiState.searchALlResults.users,
                                            onSeeDetailsClick = onSeeDetailsClick,
                                            onSeeMoreClick = onSeeMoreClick,
                                            onSeeALlClick = {
                                                pagerState.requestScrollToPage(SearchTab.Users.ordinal)
                                            }
                                        )
                                    }
                                }
                                1 -> {
                                    if (searchUiState.postTabState.state != TabState.Idle) {
                                        if (currentUser.id == GUEST_ID) {
                                            item {
                                                GuestLoginScreen {
                                                    navController.guestNavToAuth()
                                                }
                                            }
                                            return@LazyColumn
                                        }
                                        items(searchUiState.postTabState.result){
                                            PostCard(
                                                post = it,
                                                onSeeDetailsClick = onSeeDetailsClick,
                                            )
                                            LaunchedEffect(isAtBottom) {
                                                if (isAtBottom) {
                                                    Log.d("BOTTOM", "SearchScreen: Bottom Reached")
                                                    if (searchUiState.postTabState.state == TabState.Succeed && searchUiState.postTabState.nextPage) {
                                                        viewModel.searchPost()
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        viewModel.searchPost()
                                        item {
                                            LoadingScreen()
                                            Text(text = searchUiState.postTabState.messageStr)
                                        }
                                    }
                                }
                                2 -> {
                                    if (searchUiState.foodTabState.state != TabState.Idle) {
                                        items(searchUiState.foodTabState.result) { item ->
                                            ResultCardTheme(getDarkThemeConfig()) {
                                                ResultCard(
                                                    onClick = {
                                                        viewModel.ChangeScreenState(SearchScreenState.Detail)
                                                        viewModel.getRecipeDetailsById(item.idMeal)
                                                    },
                                                    recipe = item
                                                )
                                            }
                                        }
                                    } else {
                                        viewModel.searchFood()
                                        item {
                                            LoadingScreen()
                                            Text(text = searchUiState.foodTabState.messageStr)
                                        }
                                    }
                                }
                                3 -> {
                                    if (currentUser.id == GUEST_ID) {
                                        item {
                                            GuestLoginScreen {
                                                navController.guestNavToAuth()
                                            }
                                        }
                                        return@LazyColumn
                                    }
                                    item {
                                            Row(
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .padding(end = 10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Spacer(modifier = Modifier.weight(1f))
                                                Text(text = "Is filtering by: ")
                                                IconToggleButton(
                                                    checked = userChecked,
                                                    onCheckedChange = {
                                                        userChecked = it
                                                        viewModel.searchUser(it, true)
                                                    },
                                                    modifier = Modifier
                                                        .border(color = Color.Black, width = 1.dp, shape = RoundedCornerShape(10.dp))
                                                        .padding(horizontal = 5.dp)
                                                        .height(30.dp)
                                                ) {
                                                    if (userChecked) {
                                                        Text("Name")
                                                    } else {
                                                        Text("Username")
                                                    }
                                                }
                                            }
                                    }
                                    if (searchUiState.userTabState.state != TabState.Idle) {
                                        items(searchUiState.userTabState.result){
                                            UserCard(
                                                user = it,
                                                onSeeMoreClick = onSeeMoreClick
                                            )
                                            LaunchedEffect(isAtBottom) {
                                                if (isAtBottom) {
                                                    Log.d("BOTTOM", "SearchScreen: Bottom Reached")
                                                    if (searchUiState.userTabState.state == TabState.Succeed && searchUiState.userTabState.nextPage) {
                                                        viewModel.searchUser(
                                                            searchByUser = userChecked,
                                                            resetResult = false
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        viewModel.searchUser(
                                            searchByUser = userChecked,
                                            resetResult = false
                                        )
                                        item {
                                            LoadingScreen()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            SearchScreenState.Posts -> {
//                NewsfeedScreen(
//                    posts = SamplePosts.posts,
//                    onSeeDetailsClick = {
//                        viewModel.ChangeCurrentPost(it)
//                        viewModel.ChangeScreenState(SearchScreenState.Detail)
//                    }
//                )
            }
            SearchScreenState.Detail -> {
                if (loadRecipeSuccessful) {
                    CategoryDetail(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                        recipeDetail = viewModel.currentRecipeDetail,
                        navigateBackAction = {
                            viewModel.ChangeScreenState(SearchScreenState.Food)
                            viewModel.loadCurrentRecipeSuccessful.value = false
                        },
                    )
                }
            }
        }

    }
}

@Composable
private fun LazyListState.isAtBottom(): Boolean {

    return remember(this) {
        derivedStateOf {
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                        lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
            }
        }
    }.value
}

@Preview
//@Composable
//fun CardPreview() {
//    ResultCardTheme {
//        ResultCard(
//            {},
//            Recipe(
//                0,
//                "Summer Dish",
//                "Side",
//                "Japanese",
//                "",
//                ""
//            )
//        )
//    }
//}
//
//@Preview
//@Composable
//fun CardPreviewDark() {
//    ResultCardTheme(darkTheme = true) {
//        ResultCard(
//            {},
//            Recipe(
//                0,
//                "Summer Dish",
//                "Side",
//                "Japanese",
//                "",
//                ""
//            )
//        )
//    }
//}

@Preview
@Composable
fun UserCardPreview() {
    UserCard(
        user = User(
            bio = "hehehehehhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhehehehe"
        ),
        onSeeMoreClick = {}
    )
}


