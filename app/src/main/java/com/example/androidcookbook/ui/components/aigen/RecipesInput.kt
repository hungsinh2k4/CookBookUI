package com.example.androidcookbook.ui.components.aigen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R


@Composable
fun RecipesInput(
    modifier: Modifier = Modifier,
    recipes: List<String>,
    onRecipeChange: (Int, String) -> Unit,
    onDeleteRecipe: (String) -> Unit,
    addRecipe: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input fields for ingredient name and quantity
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AiGenInputLabel(
                modifier = Modifier.weight(1.2f),
                imageResource = R.drawable.recipe_icon,
                title = "Recipes",
                contentDescription = "Recipe Icon"
            )

        }

        Spacer(Modifier.height(4.dp))

        recipes.forEachIndexed { index,recipe ->
            Row(verticalAlignment = Alignment.CenterVertically) {


                OutlinedTextField(
                    modifier = Modifier
                        .weight(2f),
                    value = recipe,
                    onValueChange = { newRecipe ->
                        onRecipeChange(index, newRecipe)
                    },

                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    label = { Text("Recipe *", color = MaterialTheme.colorScheme.outline) },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        cursorColor = MaterialTheme.colorScheme.outline,
                        textColor = MaterialTheme.colorScheme.outline,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                    ),


                    )

                Spacer(Modifier.size(4.dp))




                IconButton(onClick = {
                    onDeleteRecipe(recipe)
                }, modifier = Modifier.weight(0.5f)) {
                    Icon(
                        painter = painterResource(R.drawable.delete_icon),
                        contentDescription = "Delete Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(Modifier.size(8.dp))


        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {
                addRecipe()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(4.dp))
                        .padding(8.dp)

                )
            }


            Text(
                text = "Add more ingredients",
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.outline
            )

        }
    }
    Spacer(modifier = Modifier.height(8.dp))

}