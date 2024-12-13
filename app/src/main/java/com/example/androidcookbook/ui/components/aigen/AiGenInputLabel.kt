package com.example.androidcookbook.ui.components.aigen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R

@Composable
fun AiGenInputLabel(
    modifier: Modifier = Modifier,
    imageResource: Int?,
    title: String,
    contentDescription: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (imageResource != null) {
            Icon(
                painter = painterResource(imageResource),
                contentDescription = contentDescription,
                modifier = Modifier.size(18.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.size(2.dp))
        }
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight.Bold,
        )
    }
}