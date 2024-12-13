package com.example.androidcookbook.ui.features.userprofile.edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidcookbook.ui.common.appbars.BasicTopBar
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun EditProfileTopBar(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTopBar(
        onBackButtonClick,
        text = "Edit Profile",
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
private fun EditProfileTopBarPreview() {
    AndroidCookbookTheme {
        EditProfileTopBar(
            onBackButtonClick = {},
            modifier = Modifier
        )
    }
}