package com.example.androidcookbook.ui.common.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val boldRegex = Regex("(?<!\\*)\\*\\*(?!\\*).*?(?<!\\*)\\*\\*(?!\\*)")

@Composable
fun TextWithBoldStrings(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    boldSpanStyle: SpanStyle = SpanStyle(
        fontWeight = FontWeight.Bold,
        fontSize = fontSize
    )
) {

    val keywords = boldRegex.findAll(text).map { it.value }.toList()

    val annotatedString = buildAnnotatedString {
        var startIndex = 0
        keywords.forEach { keyword ->
            val indexOf = text.indexOf(keyword)
            append(text.substring(startIndex, indexOf))
            startIndex = indexOf + keyword.length
            val newKeyWord = keyword.removeSurrounding("**")
            withStyle(
                style = boldSpanStyle
            ) {
                append(newKeyWord)
            }
        }
        append(text.substring(startIndex, text.length))

    }

    Text(
        modifier = modifier,
        fontSize = fontSize,
        text = annotatedString,
        style = style,
    )
}