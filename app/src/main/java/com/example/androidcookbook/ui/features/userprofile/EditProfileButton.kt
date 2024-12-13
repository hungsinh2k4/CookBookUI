package com.example.androidcookbook.ui.features.userprofile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun EditProfileButton(
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedIconButton (
        onClick = onEditProfileClick,
        colors = IconButtonDefaults.iconButtonColors(
//            containerColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        border = ButtonDefaults.outlinedButtonBorder().copy(width = 1.dp),
//        modifier = Modifier.border(
//            width = 1.dp,
//            color = MaterialTheme.colorScheme.onSurface,
//            shape = RoundedCornerShape(45),
//        )
    ) {
//        Text(
//            text = "Edit profile",
//        )
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = "Edit profile",
        )
    }
}

@Composable
@Preview
private fun EditProfileButtonPreview() {
    AndroidCookbookTheme(darkTheme = true) {
        UserProfileHeader(
            bannerPath = null,
            avatarPath = null,
            navigateToEditProfile = {},
            headerButton = {
                EditProfileButton(onEditProfileClick = {})
            }
        )
    }
}