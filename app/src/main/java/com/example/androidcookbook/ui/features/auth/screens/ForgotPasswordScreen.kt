package com.example.androidcookbook.ui.features.auth.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.features.auth.components.ClickableSeparatedText
import com.example.androidcookbook.ui.features.auth.components.InputField
import com.example.androidcookbook.ui.features.auth.components.SignButton
import com.example.androidcookbook.ui.features.auth.components.SignLayout

@Composable
fun ForgotPasswordScreen(
    email: String,
    onEmailChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String = ""
) {
    SignLayout {
        // Email input field
        Spacer(Modifier.height(15.dp))
        InputField(
            text = email,
            onChange = onEmailChanged,
            placeholderText = "Email",
            type = KeyboardType.Email,
            imeAction = ImeAction.Done,
            onDone = onSubmit,
            supportingText = supportingText
        )
        Spacer(Modifier.height(5.dp))

        // Submit button
        SignButton(
            onClick = onSubmit,
            actionText = "Continue",
            enabled = true,
        )

        // Back to Sign In clickable
        ClickableSeparatedText(
            unclickableText = "Return to ",
            clickableText = "Sign In",
            onClick = onNavigateToSignIn
        )
    }
}

@Preview
@Composable
fun ForgotPasswordPreview() {
    ForgotPasswordScreen(
        email = "",
        onEmailChanged = { _ -> },
        onSubmit = {},
        onNavigateToSignIn = {},
    )
}