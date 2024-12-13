package com.example.androidcookbook.ui.features.post.create

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.example.androidcookbook.ui.common.dialog.CardDialog
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme
import com.example.androidcookbook.ui.theme.transparentButtonColors

private enum class DialogType {
    Update,
    Add,
}

@Composable
fun AddStepDialog(
    onAddStep: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var step by remember { mutableStateOf("") }
    CardDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Add Step")
            TextField(
                value = step,
                onValueChange = { step = it },
                placeholder = { Text(text = "Step") },
                modifier = Modifier
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TransparentDialogButton(
                    text = "Cancel",
                    onClick = onDismissRequest
                )
                Spacer(Modifier.width(8.dp))
                TransparentDialogButton(
                    text = "Add",
                    onClick = { onAddStep(step) }
                )
            }
        }
    }
}

@Composable
fun UpdateStepDialog(
    step: String,
    onUpdateStep: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var newStep by remember { mutableStateOf(step) }
    CardDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Update Step")
            TextField(
                value = newStep,
                onValueChange = { newStep = it },
                placeholder = { Text(text = "Step") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onUpdateStep(newStep)
                    }
                ),
                modifier = Modifier
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TransparentDialogButton(
                    text = "Cancel",
                    onClick = onDismissRequest
                )
                Spacer(Modifier.width(8.dp))
                TransparentDialogButton(
                    text = "Update",
                    onClick = { onUpdateStep(newStep) }
                )
            }
        }
    }
}

@Composable
fun AddIngredientDialog(
    onAddIngredient: (Ingredient) -> Unit,
    onDismissRequest: () -> Unit,
) {
    IngredientLayout(
        ingredient = Ingredient("", ""),
        onDismissRequest,
        onAddIngredient,
        DialogType.Add
    )
}

@Composable
fun UpdateIngredientDialog(
    ingredient: Ingredient,
    onUpdateIngredient: (Ingredient) -> Unit,
    onDismissRequest: () -> Unit,
) {
    IngredientLayout(
        ingredient,
        onDismissRequest,
        onUpdateIngredient,
        DialogType.Update
    )
}

@Composable
private fun IngredientLayout(
    ingredient: Ingredient,
    onDismissRequest: () -> Unit,
    onConfirm: (Ingredient) -> Unit,
    type: DialogType
) {

    var newIngredient by remember { mutableStateOf(ingredient) }
    CardDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "${type.name} Ingredient")
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val focusManager = LocalFocusManager.current
                TextField(
                    value = newIngredient.name,
                    onValueChange = { newIngredient = newIngredient.copy(name = it) },
                    placeholder = { Text(text = "Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    modifier = Modifier.weight(2F)
//                        .horizontalScroll(rememberScrollState()),
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = newIngredient.quantity,
                    onValueChange = { newIngredient = newIngredient.copy(quantity = it) },
                    placeholder = { Text(text = "Quantity") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onConfirm(newIngredient)
                        }
                    ),
                    modifier = Modifier.weight(1.4F)
//                        .horizontalScroll(rememberScrollState())
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TransparentDialogButton(
                    text = "Cancel",
                    onClick = onDismissRequest
                )
                Spacer(Modifier.width(8.dp))
                TransparentDialogButton(
                    text = type.name,
                    onClick = { onConfirm(newIngredient) }
                )
            }
        }
    }
}


@Composable
fun TransparentDialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = transparentButtonColors(),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        )
    ) {
        Text(
            text = text
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TransparentDialogButtonPreview() {
    AndroidCookbookTheme(darkTheme = false) {
        TransparentDialogButton(
            text = "Add",
            onClick = {}
        )
    }
}

@Composable
@Preview
fun TransparentDialogButtonPreviewDarkTheme() {
    AndroidCookbookTheme(darkTheme = true) {
        TransparentDialogButton(
            text = "Add",
            onClick = {}
        )
    }
}

@Composable
@Preview
fun AddStepDialogPreview() {
    AddStepDialog(
        onAddStep = {},
        onDismissRequest = {}
    )
}

@Composable
@Preview
fun AddIngredientDialogPreview() {
    AddIngredientDialog(
        onAddIngredient = {},
        onDismissRequest = {}
    )
}

@Composable
@Preview
fun UpdateStepDialogPreview() {
    UpdateStepDialog(
        step = "",
        onUpdateStep = {},
        onDismissRequest = {}
    )
}

@Composable
@Preview
fun UpdateIngredientDialogPreview() {
    UpdateIngredientDialog(
        ingredient = Ingredient("", ""),
        onUpdateIngredient = {},
        onDismissRequest = {}
    )
}
