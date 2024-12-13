package com.example.androidcookbook.ui.features.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.data.providers.ThemeType

@Composable
fun SettingContainer(
    noticeChecked: Boolean,
    onNoticeCheckedChange: (Boolean) -> Unit,
    themeTypeSelected: ThemeType,
    onThemeTypeChange: (ThemeType) -> Unit
) {
    val objectColor: Color = MaterialTheme.colorScheme.onBackground
    val backGroundColor: Color = MaterialTheme.colorScheme.background
    Column(
        modifier = Modifier
            .background(
                color = backGroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Setting",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = objectColor
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Turn on notification",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = objectColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = noticeChecked,
                onCheckedChange = onNoticeCheckedChange,
                colors = SwitchDefaults.colors(objectColor)
            )
        }
        Text(
            text = "Theme",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = objectColor
        )
        ThemeType.values().forEach { theme ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (theme == themeTypeSelected),
                        onClick = {
                            onThemeTypeChange(theme)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = theme.toString(),
                    modifier = Modifier.padding(start = 20.dp),
                    color = objectColor
                )
                Spacer(modifier = Modifier.weight(1f))
                RadioButton(
                    selected = (theme == themeTypeSelected),
                    onClick = { onThemeTypeChange(theme) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = objectColor,
                        unselectedColor = objectColor
                    )
                )
            }

        }
    }
}