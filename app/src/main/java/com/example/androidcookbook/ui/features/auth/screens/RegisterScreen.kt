package com.example.androidcookbook.ui.features.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.auth.RegisterRequest
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
                viewModel.signUp(RegisterRequest(username, password, email))
            } else {
                viewModel.changeDialogMessage("Retype password not correct")
            }
        }
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
    onSignUpClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        InputField(email, onTypeEmail, "Email", KeyboardType.Email)

        InputField(username, onTypeUsername, "Username", KeyboardType.Text)

        InputField(password, onTypePassword, "Password", KeyboardType.Password)

        InputField(repassword, onRetypePassword, "Repeat your password", KeyboardType.Password)

        SignButton(onClick = onSignUpClick, actionText = "Sign Up")
    }
}
