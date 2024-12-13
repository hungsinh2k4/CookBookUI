package com.example.androidcookbook.ui.components.aigen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ServedAsInput(
    modifier: Modifier = Modifier,
    selectedOption: String,
    onServedAsClick: (String) -> Unit
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        AiGenInputLabel(
            imageResource = R.drawable.serve_icon,
            title = "Served as",
            contentDescription = "Serve Icon"
        )


        Spacer(Modifier.size(1.dp))

        var expanded by rememberSaveable { mutableStateOf(false) } // Controls menu visibility
        val options = listOf("Starter", "Main dish", "Dessert") // Dropdown options

        Row() {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(1f)// Toggles dropdown
            ) {



                OutlinedTextField(
                    value = selectedOption,
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                                onServedAsClick(option) // Update selected value
                                expanded = false // Close dropdown
                            }
                        )
                    }
                }
            }

        }

    }
}