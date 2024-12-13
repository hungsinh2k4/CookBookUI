package com.example.androidcookbook.ui.components.aigen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.R

@Composable
fun NoteInput(modifier: Modifier = Modifier, note: String, onNoteChange: (String) -> Unit) {


    Column(modifier = Modifier.padding(bottom = 4.dp)) {

        Text(
            text = "*Note",
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.outline
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 128.dp),
            value = note,
            onValueChange = onNoteChange,
            placeholder = {
                Text(
                    "Tell us what you want about this dish...",

                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFFFFF).copy(alpha = 0.75f),
                    fontSize = 14.sp

                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                textColor = Color.White,
                backgroundColor = Color(0xFF4A4A4A)
            ),
            shape = RoundedCornerShape(4.dp),

            )
    }

}

