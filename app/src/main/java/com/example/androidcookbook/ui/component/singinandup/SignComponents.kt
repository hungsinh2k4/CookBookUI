package com.example.androidcookbook.ui.component.singinandup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.R

@Composable
fun AppLogo() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.image_1),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 150.dp)
                )
                .padding(start = 11.dp, top = 11.dp, end = 11.dp, bottom = 11.dp)
        )
        Text(
            text = "CookBook",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.lobster_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
            )
        )
    }
}

@Composable
fun SignInComponents(
    username: String,
    onTypeUsername: (String) -> Unit,
    password: String,
    onTypePassword: (String) -> Unit,
    onSignInClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        InputField(username, onTypeUsername, "Username")

        InputField(password, onTypePassword, "Password")

        SignInButton {
            {}
        }

    }
}

@Composable
fun SignUpComponents(
    username: String,
    onTypeUsername: (String) -> Unit,
    password: String,
    onTypePassword: (String) -> Unit,
    repassword: String,
    onRetypePassword: (String) -> Unit,
    onSignInClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        InputField(username, onTypeUsername, "Username")

        InputField(password, onTypePassword, "Password")

        InputField(repassword, onRetypePassword, "Repeat your password")

        SignInButton {
            {}
        }

    }
}

@Composable
fun ClickableSeparatedText(
    unclickableText: String,
    clickableText: String,
    onClick: () -> Unit
) {
    Row {
        Text(
            text = unclickableText,
            color = Color.White,
            fontWeight = FontWeight(600)
        )
        Text(
            modifier = Modifier.clickable {
                onClick()
            },
            text = clickableText,
            color = Color(134, 147, 95),
            fontWeight = FontWeight(600)
        )
    }
}

@Composable
fun InputField(
    text: String,
    onChange: (String) -> Unit,
    placeholderText: String
) {
    TextField(
        modifier = Modifier
            .background(
                color = Color(0xFFD1CACB),
                shape = RoundedCornerShape(size = 30.dp)
            )
            .width(325.dp),
        value = text, onValueChange = onChange,
        placeholder = {
            Text(text = placeholderText)
        },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun SignInButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF916246)),
        shape = RoundedCornerShape(size = 30.dp)
    ) {
        Row {
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                )
            )
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}