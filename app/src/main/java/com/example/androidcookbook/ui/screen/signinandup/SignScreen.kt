package com.example.androidcookbook.ui.screen.signinandup

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcookbook.R
import com.example.androidcookbook.ui.component.singinandup.AppLogo
import com.example.androidcookbook.ui.component.singinandup.ClickableSeparatedText
import com.example.androidcookbook.ui.component.singinandup.SignInComponents
import com.example.androidcookbook.ui.component.singinandup.SignUpComponents
import com.example.androidcookbook.ui.uistate.SignUiState
import com.example.androidcookbook.ui.viewmodel.SignViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
@Composable
fun SignBackground(
    viewModel: SignViewModel,
    isSignIn: Boolean,
    onSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF251404))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawOval(
                color = Color(0xFF4F3423),
                topLeft = Offset(size.width * (-0.1f), size.height / (-14)),
                size = Size(size.width * 1.2f, size.height / 4)
            )
        }

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(start = 11.dp, top = 11.dp, end = 11.dp, bottom = 11.dp)
                .align(Alignment.TopCenter)
                .offset(y = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            AppLogo()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (isSignIn) {
                    SignInCompose(
                        onSignUpClick = {viewModel.ChangeInOrUp(false)},
                        onForgotPasswordClick = {},
                        onButtonSignInClick = onSignInClick
                    )
                } else {
                    SignUpCompose(
                        onSignInClick = {viewModel.ChangeInOrUp(true)},
                        onButtonSignInClick = onSignInClick
                    )
                }
            }
        }
    }
}

@Composable
fun SignInCompose(
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onButtonSignInClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    SignInComponents(
        username = username,
        onTypeUsername = {
            username = it
        },
        password = password,
        onTypePassword = {
            password = it
        },
        onSignInClick = onButtonSignInClick
    )

    ClickableSeparatedText(
        unclickableText = "Doesn’t have account ? ",
        clickableText = "Sign Up",
        onClick = onSignUpClick
    )
    ClickableSeparatedText(
        unclickableText = "",
        clickableText = "Forgot password ?",
        onClick = onForgotPasswordClick
    )
}

@Composable
fun SignUpCompose(
    onSignInClick: () -> Unit,
    onButtonSignInClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    SignUpComponents(
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
        onSignInClick = onButtonSignInClick
    )

    ClickableSeparatedText(
        unclickableText = "Already have an account ? ",
        clickableText = "Sign In",
        onClick = onSignInClick
    )
}

@Preview
@Composable
fun SignPreview() {
    var viewModel: SignViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    SignBackground(viewModel = viewModel, uiState.isSignIn, {})
}


@Preview
@Composable
fun TextPreview() {
    ClickableSeparatedText(
        unclickableText = "Doesn’t have account ?",
        clickableText = "Sign Up"
    ) {
        
    }
}
