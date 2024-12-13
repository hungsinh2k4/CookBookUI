package com.example.androidcookbook.ui.components.aigen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.androidcookbook.R
import com.example.androidcookbook.domain.model.aigen.AiGenFromImage
import com.example.androidcookbook.ui.features.aigen.AIGenUiState
import com.example.androidcookbook.ui.features.aigen.AiGenViewModel

@Composable
fun AiUploadResultScreen(
    modifier: Modifier = Modifier,
    result: AiGenFromImage?,
    imageUri: Uri?,
    uiState: AIGenUiState,
    viewModel: AiGenViewModel,
) {
    Box(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .border(width = 1.dp, color = Color(0xFF7F5346))
                .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp),
        ) {

            Text(
                text = "Recipe Details",
                fontFamily = FontFamily(Font(R.font.playfairdisplay_regular)),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (result != null) {
                Text(
                    text = "Ingredients:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.size(8.dp))



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

                Text(
                    text = "Recipes:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.outline
                )

                result.recipes.forEach { recipe ->

                    var checked by rememberSaveable { mutableStateOf(false) }
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "- ${recipe}",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.weight(3f)
                        )
                        Checkbox(modifier = Modifier.weight(1f),checked = checked, onCheckedChange = { it ->
                            if (it) {
                                checked = !checked
                                viewModel.addRecipe(recipe)
                            } else {
                                checked = !checked
                                viewModel.removeRecipe(recipe)
                            }
                        }, colors = CheckboxDefaults.colors(
                            uncheckedColor = MaterialTheme.colorScheme.outline,
                            checkedColor = MaterialTheme.colorScheme.outline,
                            checkmarkColor = MaterialTheme.colorScheme.surfaceContainer
                        ))
                    }
                    Spacer(modifier = Modifier.weight(1f))

                }
            } else {

                Text(
                    text = "No ingredients available",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1.5f)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                )

            }
        }
    }
}
