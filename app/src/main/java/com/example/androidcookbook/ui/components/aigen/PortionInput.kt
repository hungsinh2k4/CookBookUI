package com.example.androidcookbook.ui.components.aigen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.R

@Composable
fun PortionInput(
    modifier: Modifier = Modifier,
    portion: String,
    onPortionChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        AiGenInputLabel(
            imageResource = R.drawable.portion_icon, title = "Portion",
            contentDescription = "Portion Icon"
        )

        Spacer(Modifier.size(4.dp))

//        TextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp),
//            value = portion,
//            onValueChange = onPortionChange,
//            placeholder = {
//                Text(
//                    "5",
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
//                    modifier = Modifier.fillMaxSize(),
//                    color = Color(0xFFFFFFFF).copy(alpha = 0.75f),
//                    fontSize = 14.sp
//
//                )
//            },
//            singleLine = true,
//            colors = TextFieldDefaults.textFieldColors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                cursorColor = Color.White,
//                textColor = Color.White,
//                backgroundColor = Color(0xFF4A4A4A)
//            ),
//            shape = RoundedCornerShape(4.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(
//                imeAction = ImeAction.Next
//            )
//            )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            value = portion,

            onValueChange = onPortionChange,

            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.outline,
                textColor = MaterialTheme.colorScheme.outline,
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            textStyle = TextStyle(textAlign = TextAlign.Center)
            )
    }
}