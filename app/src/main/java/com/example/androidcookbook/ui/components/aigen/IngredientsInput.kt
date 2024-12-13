package com.example.androidcookbook.ui.components.aigen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R
import com.example.androidcookbook.domain.model.ingredient.Ingredient


@Composable
fun IngredientsInput(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>,
    onIngredientNameChange: (Int, String) -> Unit,
    onIngredientQuantityChange: (Int, String) -> Unit,
    onDeleteIngredient: (Int) -> Unit,
    addIngredient: () -> Unit
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
                imageResource = R.drawable.ingredient_icon,
                title = "Ingredients",
                contentDescription = "Ingredient Icon"
            )
//            Spacer(Modifier.weight(1f))
            AiGenInputLabel(
                modifier = Modifier.weight(1f),
                imageResource = null,
                title = "Quantity"
            )
        }

        Spacer(Modifier.height(4.dp))

        ingredients.forEachIndexed { index, (ingredient, quantity) ->
            Row(verticalAlignment = Alignment.CenterVertically) {


                OutlinedTextField(
                    modifier = Modifier
                        .weight(2f),
                    value = ingredient,
                    onValueChange = { newIngredient ->
                        onIngredientNameChange(index, newIngredient)
                    },

                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    label = { Text("Ingredient *", color = MaterialTheme.colorScheme.outline) },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        cursorColor = MaterialTheme.colorScheme.outline,
                        textColor = MaterialTheme.colorScheme.outline,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                    ),


                    )

                Spacer(Modifier.size(4.dp))



                OutlinedTextField(
                    modifier = Modifier
                        .weight(1.2f),
                    value = quantity,
                    onValueChange = { newQuantity ->
                        onIngredientQuantityChange(
                            index,
                            newQuantity
                        )
                    },

                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    label = { Text("Quantity *", maxLines = 1, softWrap = false, color = MaterialTheme.colorScheme.outline) },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        cursorColor = MaterialTheme.colorScheme.outline,
                        textColor = MaterialTheme.colorScheme.outline,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                    ),



                    )

                IconButton(onClick = {
                    onDeleteIngredient(index)
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
                addIngredient()
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


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun IngredientsInputPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        var ingredients = mutableListOf<Ingredient>()

        IngredientsInput(
            ingredients = ingredients,
            onIngredientNameChange = { index, it -> },
            addIngredient = {},
            onIngredientQuantityChange = { index, it -> },
            onDeleteIngredient = {})
    }
}
