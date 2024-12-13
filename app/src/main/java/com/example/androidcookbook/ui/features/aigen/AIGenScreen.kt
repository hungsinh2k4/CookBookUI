package com.example.androidcookbook.ui.features.aigen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.R
import com.example.androidcookbook.domain.model.aigen.AiResult
import com.example.androidcookbook.domain.usecase.createImageRequestBody
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.components.SimpleNavigateUpTopBar
import com.example.androidcookbook.ui.components.aigen.DashedLine
import com.example.androidcookbook.ui.components.aigen.DetectAnimation
import com.example.androidcookbook.ui.components.aigen.IngredientsInput
import com.example.androidcookbook.ui.components.aigen.NoteInput
import com.example.androidcookbook.ui.components.aigen.RecipesInput
import com.example.androidcookbook.ui.components.aigen.setAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AIGenScreen(
    aiGenViewModel: AiGenViewModel,
    cookbookViewModel: CookbookViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val uiState = aiGenViewModel.aiGenUiState.collectAsState().value
    val selectedImageUri by aiGenViewModel.selectedImageUri.collectAsState()

    if (uiState.isTakingInput) {
        cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
        cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
    }

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {


        if (uiState.isTakingInput || uiState.isDone) {
            item {
                Text(
                    text = "AI Chef",
                    fontFamily = FontFamily(Font(R.font.playfairdisplay_regular)),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 36.sp
                )
            }
        }

        if (uiState.isTakingInput) {

            item {
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Default)
                cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.Default)
                TakingInputScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    viewModel = aiGenViewModel,
                    uiState = uiState,
                    context = context,
                    scope = scope,
                )
            }


        } else if (uiState.isProcessing) {
            item {
                BackHandler { aiGenViewModel.updateIsTakingInput() }
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {
                    SimpleNavigateUpTopBar(
                        navigateBackAction = {
                            aiGenViewModel.updateIsTakingInput()
                        },
                        title = "",
                        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                    )
                })
                cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
                setAnimation()
            }
        } else if (uiState.isDoneUploadingImage) {
            item {
                BackHandler { aiGenViewModel.updateIsTakingInput() }
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {
                    SimpleNavigateUpTopBar(
                        navigateBackAction = {
                            aiGenViewModel.updateIsTakingInput()
                        },
                        title = "",
                        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                    )
                })
                cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
                DetectAnimation()
            }
        } else if (uiState.isDone) {
            item {
                BackHandler { aiGenViewModel.updateIsTakingInput() }
                cookbookViewModel.updateTopBarState(CookbookUiState.TopBarState.Custom {
                    SimpleNavigateUpTopBar(
                        navigateBackAction = {
                            aiGenViewModel.updateIsTakingInput()
                        },
                        title = "",
                        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                    )
                })
                cookbookViewModel.updateBottomBarState(CookbookUiState.BottomBarState.NoBottomBar)
                aiGenViewModel.aiResult.value?.let {
                    FinalResultScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        result = it
                    )
                }
            }
        }

        if (uiState.isTakingInput) {
            item {
                Button(
                    onClick = {
                        aiGenViewModel.getAiResult()
                    },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Text(
                        "Let's cook", fontFamily = FontFamily(Font(R.font.playfairdisplay_regular)),
                        color = Color.Black,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TakingInputScreen(
    modifier: Modifier = Modifier,
    uiState: AIGenUiState,
    viewModel: AiGenViewModel,
    context: Context,
    scope: CoroutineScope
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {


            scope.launch {
                val uri: Uri? = result.data?.data
                viewModel.updateSelectedUri(uri)
                viewModel.updateIsDoneUploadingImage()

                val body = uri?.let { createImageRequestBody(context, it) }

                val responseResult = body?.let { viewModel.uploadImage(it) }

                if (responseResult != null) {
                    if (viewModel.aiGenUiState.value.isDoneUploadingImage) {
                        Log.d("Upload", "Upload successful")
                        viewModel.clearRecipes()
                        viewModel.clearIngredients()
                        viewModel.uploadResponse.value?.ingredients?.forEach { ingredient ->
                            viewModel.addIngredient(ingredient)
                        }
                        viewModel.uploadResponse.value?.recipes?.forEach { recipe ->
                            viewModel.addRecipe(recipe)
                        }
                    }
                } else {
                    Log.d("Upload", "Upload failed.")
                }
                viewModel.updateIsTakingInput()
            }

        }
    }


    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
                .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp),
        ) {
            RecipesInput(
                recipes = uiState.recipes,
                addRecipe = { viewModel.addEmptyRecipe() },
                onRecipeChange = { index, recipe ->
                    viewModel.updateRecipe(
                        index,
                        recipe
                    )
                    viewModel.setIsEmpty(false)
                },
                onDeleteRecipe = { viewModel.removeRecipe(it) }
            )

            //TODO Served As
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {


                OutlinedIconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_PICK).apply {
                            type = "image/*"
                        }
                        launcher.launch(intent)

                    },
                    modifier = Modifier
                        .padding(top = 12.dp),
                    border = BorderStroke(1.dp,Color.Black)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.gallery_icon),
                        contentDescription = "Camera",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            //TODO Dashed line
            DashedLine(
                color = MaterialTheme.colorScheme.outline,
                dashWidth = 6f,
                dashGap = 6f,
                strokeWidth = 2f
            )


            Spacer(modifier = Modifier.size(8.dp))

            //TODO Ingredient adding
            IngredientsInput(
                ingredients = uiState.ingredients,
                onIngredientNameChange = { index, it ->
                    viewModel.updateIngredientName(
                        index,
                        it
                    )
                },
                onIngredientQuantityChange = { index, it ->
                    viewModel.updateIngredientQuantity(
                        index,
                        it
                    )
                },
                onDeleteIngredient = { viewModel.deleteIngredient(it) },
                addIngredient = { viewModel.addEmptyIngredient() })


            DashedLine(
                color = MaterialTheme.colorScheme.outline,
                dashWidth = 6f,
                dashGap = 6f,
                strokeWidth = 2f
            )

            Spacer(modifier = Modifier.size(8.dp))

            NoteInput(note = uiState.note, onNoteChange = { viewModel.updateNote(it) })

            val textColor = if (!viewModel.isFieldEmpty()) {
                MaterialTheme.colorScheme.surfaceContainer
            } else {
                MaterialTheme.colorScheme.error
            }
            Text(
                text = "Please supply atleast one ingredient", color = textColor
            )
        }
    }
}

@Composable
fun FinalResultScreen(modifier: Modifier = Modifier, result: AiResult) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .border(width = 1.dp, color = Color(0xFF7F5346))
                .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)

        ) {

            result.recipes.forEach { cookingInstruction ->

                Text(
                    text = cookingInstruction.name,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.lobster_regular)),
                    fontSize = 28.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(20.dp))

                Text(
                    text = "Ingredients",
                    color = Color.DarkGray,
                    fontFamily = FontFamily(Font(R.font.playfairdisplay_regular)),
                    fontSize = 24.sp
                )

                Spacer(Modifier.size(10.dp))

                cookingInstruction.ingredients.forEach { ingredient ->
                    Text(text = ingredient.name + " - " + ingredient.quantity)
                    Spacer(Modifier.size(10.dp))
                    DashedLine()
                }

                Spacer(Modifier.size(20.dp))

                Text(
                    text = "Instruction",
                    color = Color.DarkGray,
                    fontFamily = FontFamily(Font(R.font.playfairdisplay_regular)),
                    fontSize = 24.sp
                )

                Spacer(Modifier.size(10.dp))


                cookingInstruction.steps.forEach { step ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        // Bullet
                        Text(
                            text = "•",
                            color = Color.Black,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        // Step description
                        Text(
                            text = step,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        )
                    }


                }

                Spacer(Modifier.size(20.dp))

                Divider(color = Color.DarkGray)

                Spacer(Modifier.size(20.dp))
            }
        }
    }
}

