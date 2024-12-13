package com.example.androidcookbook.ui.features.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.auth.RegisterRequest
import com.example.androidcookbook.ui.common.dialog.MinimalDialog
import com.example.androidcookbook.ui.features.auth.AuthViewModel
import com.example.androidcookbook.ui.features.auth.components.ClickableSeparatedText
import com.example.androidcookbook.ui.features.auth.components.InputField
import com.example.androidcookbook.ui.features.auth.components.SignButton
import com.example.androidcookbook.ui.features.auth.components.SignLayout

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignIn: () -> Unit,
) {
    SignLayout {
        SignUpCompose(
            onSignInClick = onNavigateToSignIn,
            viewModel = authViewModel,
        )
    }
    val authUiState = authViewModel.uiState.collectAsState().value
    if (authUiState.openDialog) {
        MinimalDialog(
            dialogMessage = authUiState.dialogMessage,
            onDismissRequest = {
                authViewModel.changeOpenDialog(false)
            }
        )
    }
}

@Composable
fun SignUpCompose(
    onSignInClick: () -> Unit,
    viewModel: AuthViewModel,
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    val (first, second, third, fourth) = remember { FocusRequester.createRefs() }
    SignUpComponents(
        email = email,
        onTypeEmail = {
            email = it
        },
        username = username,
        onTypeUsername = {
            username = it
        },
        password = password,
        onTypePassword = {
            password = it
        },
        repassword = repassword,
        onRetypePassword = {
            repassword = it
        },
        onSignUpClick = {
            if (password == repassword) {
                viewModel.signUp(RegisterRequest(username.trim(), password, email))
            } else {
                viewModel.changeDialogMessage("Retype password not correct")
                viewModel.changeOpenDialog(true)
            }
        },
        first, second, third, fourth
    )

    ClickableSeparatedText(
        unclickableText = "Already have an account ? ",
        clickableText = "Sign In",
        onClick = onSignInClick
    )
}

@Composable
fun SignUpComponents(
    email: String,
    onTypeEmail: (String) -> Unit,
    username: String,
    onTypeUsername: (String) -> Unit,
    password: String,
    onTypePassword: (String) -> Unit,
    repassword: String,
    onRetypePassword: (String) -> Unit,
    onSignUpClick: () -> Unit,
    first: FocusRequester,
    second: FocusRequester,
    third: FocusRequester,
    fourth: FocusRequester
) {
    val focusManager = LocalFocusManager.current
    val changeFocus: () -> Unit = {
        focusManager.moveFocus(FocusDirection.Next)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        InputField(email, onTypeEmail, "Email", KeyboardType.Email,
            onDone = changeFocus,
            modifier = Modifier.focusRequester(first)
                .focusProperties { next = second },
            imeAction = ImeAction.Next
        )

        InputField(username, onTypeUsername, "Username", KeyboardType.Text,
            onDone = changeFocus,
            modifier = Modifier.focusRequester(second)
                .focusProperties { next = third },
            imeAction = ImeAction.Next
        )

        InputField(password, onTypePassword, "Password", KeyboardType.Password,
            onDone = changeFocus,
            modifier = Modifier.focusRequester(third)
                .focusProperties { next = fourth },
            imeAction = ImeAction.Next
        )

        InputField(repassword, onRetypePassword, "Repeat your password", KeyboardType.Password,
            onDone = onSignUpClick,
            modifier = Modifier.focusRequester(fourth),
            imeAction = ImeAction.Done
        )

        SignButton(onClick = onSignUpClick, actionText = "Sign Up", enabled = true)
    }
}
