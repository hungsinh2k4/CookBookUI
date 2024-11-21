package com.example.androidcookbook.ui.features.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.features.auth.components.ClickableSeparatedText
import com.example.androidcookbook.ui.features.auth.components.ClickableText
import com.example.androidcookbook.ui.features.auth.components.InputField
import com.example.androidcookbook.ui.features.auth.components.SignButton
import com.example.androidcookbook.ui.features.auth.components.SignLayout

const val USERNAME_TEXT_FIELD_TEST_TAG = "username"
const val PASSWORD_TEXT_FIELD_TEST_TAG = "password"

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onSignInClick: (String, String) -> Unit,
    onUseAsGuest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SignLayout {
        SignInComponent(
            onSignInClick = onSignInClick,
            onForgotPasswordClick = onForgotPasswordClick,
            onSignUpClick = onNavigateToSignUp,
        )
        ClickableText(
            clickableText = "Use as guest",
            onClick = onUseAsGuest,
        )
    }
}

@Composable
fun SignInComponent(
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        InputField(username, { username = it },"Username", KeyboardType.Text,
            Modifier.testTag(USERNAME_TEXT_FIELD_TEST_TAG)
        )

        InputField(password, { password = it },"Password", KeyboardType.Password,
            Modifier.testTag(PASSWORD_TEXT_FIELD_TEST_TAG)
        )

        SignButton(
            onClick = { onSignInClick(username, password) },
            actionText = "Sign In"
        )
    }

    ClickableSeparatedText(
        unclickableText = "Doesn’t have account ? ",
        clickableText = "Sign Up",
        onClick = onSignUpClick,
    )
    ClickableSeparatedText(
        unclickableText = "",
        clickableText = "Forgot password ?",
        onClick = onForgotPasswordClick
    )
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(
        {}, {}, { _, _->},{},
    )
}