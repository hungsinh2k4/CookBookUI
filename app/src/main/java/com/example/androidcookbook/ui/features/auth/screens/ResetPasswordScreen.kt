package com.example.androidcookbook.ui.features.auth.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.features.auth.components.ClickableSeparatedText
import com.example.androidcookbook.ui.features.auth.components.InputField
import com.example.androidcookbook.ui.features.auth.components.SignButton
import com.example.androidcookbook.ui.features.auth.components.SignLayout

@Composable
fun ResetPasswordScreen(
    password: String,
    onPasswordChange: (String) -> Unit,
    retypePassword: String,
    onRetypePasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String = ""
) {
    SignLayout {
        val focusManager = LocalFocusManager.current
        val changeFocus: () -> Unit = {
            focusManager.moveFocus(FocusDirection.Next)
        }
        val (retype) = remember { FocusRequester.createRefs() }
        Spacer(Modifier.height(15.dp))

        // Password Field
        InputField(
            text = password,
            onChange = onPasswordChange,
            placeholderText = "New password",
            type = KeyboardType.Password,
            imeAction = ImeAction.Next,
            onDone = changeFocus,
            modifier = Modifier.focusProperties { next = retype },
        )

        // RetypePassword Field
        InputField(
            text = retypePassword,
            onChange = onRetypePasswordChange,
            placeholderText = "Retype new password",
            type = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onDone = onSubmit,
            modifier = Modifier.focusRequester(retype),
            supportingText = supportingText
        )

        // Submit button
        SignButton(
            onClick = onSubmit,
            actionText = "Submit",
            enabled = true
        )

        // ClickableText("Return to Sign In")
        ClickableSeparatedText(
            unclickableText = "Return to ",
            clickableText = "Sign In",
            onClick = onNavigateToSignIn
        )
    }
}

@Preview
@Composable
fun ResetPasswordPreview() {
    ResetPasswordScreen(
        password = "",
        onPasswordChange = {},
        retypePassword = "",
        onRetypePasswordChange = {},
        onSubmit = {},
        onNavigateToSignIn = {}
    )
}