package com.example.androidcookbook.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun CreatePostScreen(
    onPostButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler { onBackButtonClick() }

    var postTitle by remember { mutableStateOf("") }
    var postBody by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        PostHeader(modifier = Modifier.padding(bottom = 8.dp))

        // Post Title
        TextField(
            value = postTitle,
            onValueChange = { value -> postTitle = value },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = "What's new !",
                )
            },
            textStyle = MaterialTheme.typography.titleMedium
        )

        Image(
            painter = painterResource(R.drawable.place_holder_shrimp_post_image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(vertical = 16.dp)
        )

        // Post Body
        TextField(
            value = postBody,
            onValueChange = { value -> postBody = value },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Post body"
                )
            },
            minLines = 5,
        )

        Button(
            onClick = onPostButtonClick,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Text(text = "Post")
        }
    }
}

@Composable
@Preview
fun CreatePostScreenPreview() {
    AndroidCookbookTheme(darkTheme = false) {
        CreatePostScreen(
            {},
            {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
@Preview
fun CreatePostScreenPreviewDarkTheme() {
    AndroidCookbookTheme(darkTheme = true) {
        CreatePostScreen(
            {},
            {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}