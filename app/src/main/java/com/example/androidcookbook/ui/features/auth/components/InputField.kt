package com.example.androidcookbook.ui.features.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    type: KeyboardType,
    modifier: Modifier = Modifier
) {
    when (type) {
        KeyboardType.Password -> PasswordInputField(
            text = text,
            onChange = onChange,
            placeholderText = placeholderText,
            modifier = modifier
        )
        else -> DefaultInputField(
            text = text,
            onChange = onChange,
            placeholderText = placeholderText,
            type = type,
            modifier = modifier
        )
    }
}

@Composable
private fun PasswordInputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
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
                Icons.Outlined.Lock
            else Icons.Filled.Lock

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        keyboardType = KeyboardType.Password
    )
}

@Composable
private fun DefaultInputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    type: KeyboardType,
    modifier: Modifier = Modifier
) {
    BaseTextField(
        text = text,
        onChange = onChange,
        placeholderText = placeholderText,
        modifier = modifier,
        visualTransformation = VisualTransformation.None,
        trailingIcon = null,
        keyboardType = type
    )
}

@Composable
private fun BaseTextField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType
) {
    TextField(
        modifier = modifier
            .background(
                color = Color(0xFFD1CACB),
                shape = RoundedCornerShape(size = 30.dp)
            )
            .width(325.dp),
        value = text,
        onValueChange = onChange,
        placeholder = { Text(text = placeholderText) },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        singleLine = true
    )
}
