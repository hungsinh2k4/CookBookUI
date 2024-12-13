package com.example.androidcookbook.ui.features.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidcookbook.R
import com.example.androidcookbook.data.providers.ThemeType
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.recipe.Recipe
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.features.newsfeed.NewsfeedCard
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun ResultCard(
    onClick: () -> Unit,
    recipe: Recipe
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .height(120.dp)
                .padding(start = 10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipe.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = recipe.strMeal,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = recipe.strCategory,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = recipe.strArea,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.alpha(0.75F),
        )
    }
}

@Composable
fun UserCard(
    user: User,
    onSeeMoreClick: (User) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onSeeMoreClick(user)
            }
    ) {
        Row(
            modifier = Modifier.height(140.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
                    .clip(CircleShape)
                    .weight(1f),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(user.avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.name,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .align(Alignment.Start),
                ) {
                    Text(
                        text = user.totalFollowers.toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " followers",
                        fontSize = 15.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Start),
                ) {
                    Text(
                        text = user.totalFollowing.toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " followings",
                        fontSize = 15.sp
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.alpha(0.75F),
        )
    }
}

@Composable
fun PostCard(
    post: Post,
    onSeeDetailsClick: (Post) -> Unit
) {
    val cookbookViewModel = hiltViewModel<CookbookViewModel>()
    val darkTheme = when(cookbookViewModel.themeType.collectAsState().value) {
        ThemeType.Default -> isSystemInDarkTheme()
        ThemeType.Dark -> true
        ThemeType.Light -> false
    }
    ResultCardTheme(darkTheme) {
        NewsfeedCard(
            post = post,
            onSeeDetailsClick = onSeeDetailsClick,
            currentUser = User(),
            onDeletePost = {},
            onEditPost = {},
            onUserClick = {},
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = Color.Transparent
                )
        )
    }
}

@Composable
fun SearchAllResultsScreen(
    posts: List<Post>,
    users: List<User>,
    onSeeDetailsClick: (Post) -> Unit,
    onSeeMoreClick: (User) -> Unit,
    onSeeALlClick: () -> Unit
) {
    var postsIndex = 0
    var usersIndex = 0
    val maxPosts =
        if (posts.count() > 3) 3
        else posts.count()
    val maxUsers =
        if (users.count() > 3) 3
        else users.count()
    Text(
        text = "Everyone",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 10.dp)
    )
    while (usersIndex < maxUsers) {
        Spacer(modifier = Modifier.height(10.dp))
        UserCard(
            user = users[usersIndex],
            onSeeMoreClick = onSeeMoreClick
        )
        usersIndex++
    }
    Button(
        onClick = onSeeALlClick,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            Color.White
        ),
        border = BorderStroke(1.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Text(
            text = "See All",
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Posts",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 10.dp)
    )
    while (postsIndex < maxPosts) {
        Spacer(modifier = Modifier.height(10.dp))
        PostCard(
            post = posts[postsIndex],
            onSeeDetailsClick = onSeeDetailsClick
        )
        postsIndex++
    }
}



@Preview
@Composable
fun UserPreview() {

}
