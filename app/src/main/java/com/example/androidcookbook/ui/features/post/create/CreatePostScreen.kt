package com.example.androidcookbook.ui.features.post.create

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.components.post.PostHeader
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme
import java.time.LocalDate

enum class CreatePostType {
    Post,
    Update
}

@Composable
fun CreatePostScreen(
    author: User,
    postTitle: String,
    updatePostTitle: (String) -> Unit,
    postBody: String,
    updatePostBody: (String) -> Unit,
    recipe: List<String>,
    onAddNewStep: () -> Unit,
    updateStep: (Int) -> Unit,
    deleteStep: (Int) -> Unit,
    ingredients: List<Ingredient>,
    onAddNewIngredient: () -> Unit,
    updateIngredient: (Int) -> Unit,
    deleteIngredient: (Int) -> Unit,
    postImageUri: Uri?,
    updatePostImageUri: (Uri?) -> Unit,
    onPostButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    cookTime: String,
    onCookTimeChange: (String) -> Unit,
    createType: CreatePostType,
    onUserClick: (User) -> Unit,
    showErrorMessage: Boolean = false,
    modifier: Modifier = Modifier,
) {
    BackHandler { onBackButtonClick() }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = updatePostImageUri
    )

    val focusManager = LocalFocusManager.current
    val (title, description) = remember { FocusRequester.createRefs() }

//    val imeState = rememberImeState()
//    val scrollState = rememberScrollState()
//
//    LaunchedEffect(imeState.value) {
//        if (imeState.value) {
//            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
//        }
//    }
    Column {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            PostHeader(
                author = author,
                createdAt = LocalDate.now().toString(),
                onUserClick = onUserClick,
                authorFontSize = 19.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Post
            AddPostContent(
                postTitle,
                updatePostTitle,
                title,
                description,
                focusManager,
                postBody,
                updatePostBody,
                showErrorMessage,
            )

            Spacer(Modifier.height(16.dp))

            // Recipe
            AddSteps(recipe, updateStep, deleteStep, onAddNewStep)

            Spacer(Modifier.height(16.dp))

            // Ingredients
            AddIngredients(ingredients, updateIngredient, deleteIngredient, onAddNewIngredient)

            CookTime(cookTime, onCookTimeChange, Modifier.padding(vertical = 8.dp))

            CreatePostImage(postImageUri, singlePhotoPickerLauncher)
        }
        BottomPostRow(onPostButtonClick, createType)
    }
}

@Composable
private fun CookTime(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "Cook time:",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.width(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text("How long will it take?")
            }
        )
    }
}

@Composable
private fun AddPostContent(
    postTitle: String,
    updatePostTitle: (String) -> Unit,
    title: FocusRequester,
    description: FocusRequester,
    focusManager: FocusManager,
    postBody: String,
    updatePostBody: (String) -> Unit,
    showErrorMessage: Boolean = false,
) {
    Text(
        text = "Post",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    TextField(
        value = postTitle,
        onValueChange = updatePostTitle,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester = title)
            .focusProperties {
                down = description
            },
        placeholder = {
            Text(
                text = "Title",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardOptions.Default.capitalization,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        textStyle = MaterialTheme.typography.titleMedium,
        singleLine = true,
    )
    TextField(
        value = postBody,
        onValueChange = updatePostBody,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester = description),
        placeholder = {
            Text(
                text = "Description"
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Default
        ),
        minLines = 5,
        supportingText = {
            if (showErrorMessage) {
                Text(
                    text = "Post title/description cannot be empty",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Composable
private fun AddSteps(
    recipe: List<String>,
    updateStep: (Int) -> Unit,
    deleteStep: (Int) -> Unit,
    onAddNewStep: () -> Unit,
) {
    Text(
        text = "Recipe",
        style = MaterialTheme.typography.titleMedium,
    )
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        recipe.forEachIndexed { index, step ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { updateStep(index) }
                        .weight(1f)
                ) {
                    Text(
                        text = "${index + 1}. ",
                        modifier = Modifier
                    )
                    Text(
                        text = step,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                    )
                }

//                    TextField(
//                        value = step,
//                        onValueChange = {
//
//                        },
//                        trailingIcon = {
//
//                        },
//                        colors = transparentTextFieldColors(),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )

                IconButton(
                    onClick = {
                        deleteStep(index)
                    },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Delete step",
                    )
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onAddNewStep() }
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AddBox,
                contentDescription = "Add recipe"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Add step",
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun AddIngredients(
    ingredients: List<Ingredient>,
    updateIngredient: (Int) -> Unit,
    deleteIngredient: (Int) -> Unit,
    onAddNewIngredient: () -> Unit,
) {
    Text(
        text = "Ingredients",
        style = MaterialTheme.typography.titleMedium,
    )
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        ingredients.forEachIndexed { index, ingredient ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clickable { updateIngredient(index) }
                        .weight(1f)
                ) {
                    Text(
                        text = "${index + 1}. "
                    )
                    Text(
                        text = "${ingredient.name} - ${ingredient.quantity}",
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                    )
                }

                IconButton(
                    onClick = {
                        deleteIngredient(index)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Delete ingredient"
                    )
                }
            }
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onAddNewIngredient() }
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AddBox,
                contentDescription = "Add ingredient"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Add ingredient",
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun BottomPostRow(
    onPostButtonClick: () -> Unit,
    createType: CreatePostType,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Anyone can see and reply",
            color = LocalContentColor.current.copy(alpha = 0.6f),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(Modifier.weight(1F))
        Button(
            onClick = onPostButtonClick,
            modifier = Modifier
        ) {
            Text(text = createType.name)
        }
    }
}

@Composable
private fun CreatePostImage(
    postImageUri: Uri?,
    singlePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
) {
    if (postImageUri != null) {
        AsyncImage(
            model = postImageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
        )
    } else {
        Button(
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Text("Add image")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreatePostScreenPreview() {
    AndroidCookbookTheme(darkTheme = false) {
        CreatePostScreen(
            User(),
            "", {},
            "", {},
            listOf("Preheat oven to 350 degrees F (175 degrees C). ", "Cook noodle"),
            {},{},{},
            listOf(
                Ingredient("Rice", "1kg"),
                Ingredient("Noodle", "1kg")
            ),
            {},{},{},
            null, {},
            {},
            {},
            cookTime = "1h",
            onCookTimeChange = {},
            createType = CreatePostType.Post,
            onUserClick = {},
//            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
@Preview
fun CreatePostScreenPreviewDarkTheme() {
    AndroidCookbookTheme(darkTheme = true) {
        CreatePostScreen(
            User(),
            "", {},
            "", {},
            listOf("Preheat oven to 350 degrees F (175 degrees C). ", "Cook noodle"),
            {},{},{},
            listOf(
                Ingredient("Rice", "1kg"),
                Ingredient("Noodle", "1kg")
            ),
            {},{},{},
            null, {},
            {},
            {},
            cookTime = "1h",
            onCookTimeChange = {},
            createType = CreatePostType.Post,
            onUserClick = {},
//            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}