package com.example.androidcookbook.ui.features.auth.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.ui.features.auth.theme.SignLayoutTheme

@Composable
fun InputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    type: KeyboardType,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
    supportingText: String = "",
) {
    when (type) {
        KeyboardType.Password -> PasswordInputField(
            text = text,
            onChange = onChange,
            placeholderText = placeholderText,
            modifier = modifier,
            onDone = onDone,
            imeAction = imeAction,
            supportingText = supportingText
        )
        else -> DefaultInputField(
            text = text,
            onChange = onChange,
            placeholderText = placeholderText,
            type = type,
            modifier = modifier,
            onDone = onDone,
            imeAction = imeAction,
            supportingText = supportingText
        )
    }
}

@Composable
private fun PasswordInputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
    supportingText: String = "",
) {
    var passwordVisible by remember { mutableStateOf(false) }

    BaseTextField(
        text = text,
        onChange = onChange,
        placeholderText = placeholderText,
        modifier = modifier,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = description,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        keyboardType = KeyboardType.Password,
        onDone = onDone,
        imeAction = imeAction,
        supportingText = supportingText
    )
}

@Composable
private fun DefaultInputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    supportingText: String = "",
    type: KeyboardType,
    modifier: Modifier = Modifier,
    imeAction: ImeAction,
    onDone: () -> Unit
) {
    BaseTextField(
        text = text,
        onChange = onChange,
        placeholderText = placeholderText,
        modifier = modifier,
        visualTransformation = VisualTransformation.None,
        trailingIcon = null,
        keyboardType = type,
        onDone = onDone,
        imeAction = imeAction,
        supportingText = supportingText
    )
}

@Composable
private fun BaseTextField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    supportingText: String = "",
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier.width(325.dp),
        value = text,
        onValueChange = onChange,
        label = { Text(text = placeholderText, color = MaterialTheme.colorScheme.primary) },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        trailingIcon = trailingIcon,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.outline,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        supportingText = { Text(text = supportingText, color = MaterialTheme.colorScheme.error) }
    )
}

@Preview
@Composable
fun BaseTextPreview() {
    SignLayoutTheme {
        BaseTextField(
            text = "",
            onChange = {},
            placeholderText = "Email *",
        )
    }
}

@Preview()
@Composable
fun PasswordTextPreview() {
    SignLayoutTheme(
        darkTheme = false
    ) {
        PasswordInputField(
            text = "",
            onChange = {},
            placeholderText = "Password *",
            onDone = {},
            imeAction = ImeAction.Done
        )
    }
}
