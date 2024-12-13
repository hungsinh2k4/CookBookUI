package com.example.androidcookbook.ui.features.auth.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.ui.features.auth.components.ClickableSeparatedText
import com.example.androidcookbook.ui.features.auth.components.InputField
import com.example.androidcookbook.ui.features.auth.components.SignButton
import com.example.androidcookbook.ui.features.auth.components.SignLayout

@Composable
fun OtpCodeScreen(
    otpCode: String,
    onOtpCodeChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onGoBack: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToEmail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SignLayout {
        //TODO: Otp 6 digits field
        Text(
            text = "We have sent an OTP code to your email.\n Enter the OTP code below to verify",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))

        OtpTextField(
            otpText = otpCode,
            onOtpTextChange = {
                onOtpCodeChange(it)
            },
            imeAction = ImeAction.Done,
            onDone = {
                Log.d("OTP DEBUG", "OtpCodeScreen: ${otpCode}")
                if (otpCode.length == 6) {
                    onSubmit()
                }
            },
        )
        Spacer(Modifier.height(5.dp))
        //TODO: Submit button
        SignButton(
            onClick = {
                if (otpCode.length == 6) {
                    onSubmit()
                }
            },
            actionText = "Continue",
            enabled = otpCode.length == 6
        )

        //TODO: ClickableText("Input the wrong email? Go back")
        ClickableSeparatedText(
            unclickableText = "Input the wrong email? ",
            clickableText = "Go back",
            onClick = onNavigateToEmail
        )

        //TODO: ClickableText("Return to Sign In")
        ClickableSeparatedText(
            unclickableText = "Return to ",
            clickableText = "Sign In",
            onClick = onNavigateToSignIn
        )
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String) -> Unit,
    imeAction: ImeAction,
    onDone: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(45.dp)
            .height(50.dp)
            .border(
                when {
                    isFocused -> 2.dp
                    else -> 1.dp
                }, when {
                    isFocused -> MaterialTheme.colorScheme.inversePrimary
                    else -> MaterialTheme.colorScheme.primary
                }, RoundedCornerShape(8.dp)
            )
            .padding(5.dp),
        text = char,
        color = if (isFocused) {
            MaterialTheme.colorScheme.inversePrimary
        } else {
            MaterialTheme.colorScheme.primary
        },
        textAlign = TextAlign.Center,
        fontSize = 32.sp
    )
}