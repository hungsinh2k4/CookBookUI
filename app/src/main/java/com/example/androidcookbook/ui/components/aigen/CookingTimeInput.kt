package com.example.androidcookbook.ui.components.aigen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.R

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CookingTimeInput(
    modifier: Modifier = Modifier,
    cookingTime: String,
    onCookingTimeChange: (String) -> Unit,
    selectedOption: String,
    onOptionsClick: (String) -> Unit
) {

    Column(modifier = modifier) {
        // Label
        AiGenInputLabel(
            imageResource = R.drawable.clock_icon,
            title = "Cooking time",
            contentDescription = "Clock Icon"
        )

        Spacer(Modifier.size(4.dp))

        // Time picking
        Row() {

//            TextField(
//                modifier = Modifier
//                    .weight(1.5f)
//                    .height(48.dp),
//                value = cookingTime,
//                onValueChange = onCookingTimeChange,
//                placeholder = {
//                    Text(
//                        "1",
//                        textAlign = TextAlign.Center,
//                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
//                        modifier = Modifier.fillMaxSize(),
//                        color = Color(0xFFFFFFFF).copy(alpha = 0.5f),
//                        fontSize = 14.sp
//
//                    )
//                },
//                singleLine = true,
//                colors = TextFieldDefaults.textFieldColors(
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    cursorColor = Color.White,
//                    textColor = Color.White,
//                    backgroundColor = Color(0xFF4A4A4A)
//                ),
//                shape = RoundedCornerShape(4.dp),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Next
//                )
//                )

            OutlinedTextField(
                modifier = Modifier
                    .weight(1.5f)
                    .height(44.dp),
                value = cookingTime,
                onValueChange = onCookingTimeChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.outline,
                    textColor = MaterialTheme.colorScheme.outline,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                textStyle = TextStyle(textAlign = TextAlign.Center)

                )


            var expanded by rememberSaveable { mutableStateOf(false) } // Controls menu visibility
//            var selectedOption by remember { mutableStateOf("Minute") } // Holds the selected value
            val options = listOf("Minute", "Hour", "Day") // Dropdown options
            //kind of time to pick
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }, // Toggles dropdown
                modifier = Modifier.weight(4f)
            ) {

                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true, // Makes it non-editable
                    trailingIcon = { // Dropdown icon
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.outline,
                        textColor = MaterialTheme.colorScheme.outline,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    singleLine = true,

                    )
                // The dropdown menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    } // Closes menu when tapped outside
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                onOptionsClick(option) // Update selected value
                                expanded = false // Close dropdown
                            }
                        )
                    }
                }
            }


        }

    }
}