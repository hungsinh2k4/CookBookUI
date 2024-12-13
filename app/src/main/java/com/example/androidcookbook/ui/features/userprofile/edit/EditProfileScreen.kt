package com.example.androidcookbook.ui.features.userprofile.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.ui.features.userprofile.components.UserAvatar
import com.example.androidcookbook.ui.features.userprofile.components.UserBanner
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme
import com.example.androidcookbook.ui.theme.transparentTextFieldColors

@Composable
fun EditProfileScreen(
    avatarPath: Uri?,
    updateAvatarUri: (Uri?) -> Unit,
    bannerPath: Uri?,
    updateBannerUri: (Uri?) -> Unit,
    name: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    bio: TextFieldValue,
    onBioChange: (TextFieldValue) -> Unit,
    onBackButtonClick: () -> Unit,
    onUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        EditProfileTopBar(onBackButtonClick = onBackButtonClick)
        HorizontalDivider()

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1F)
                .verticalScroll(rememberScrollState())
        ) {
            val avatarPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = updateAvatarUri,
            )
            EditProfileItem(
                text = "Avatar",
                onChange = {
                    avatarPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier,
            ) {
                UserAvatar(
                    avatarPath = avatarPath,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                        avatarPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }

            HorizontalDivider()
            val bannerPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = updateBannerUri,
            )
            EditProfileItem(
                text = "Banner",
                onChange = {
                    bannerPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier,
            ) {
                UserBanner(
                    bannerPath = bannerPath,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clickable {
                        bannerPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            HorizontalDivider()

            val nameFocusRequester = remember { FocusRequester() }
            EditProfileItem(
                text = "Name",
                onChange = { nameFocusRequester.requestFocus() },
                modifier = Modifier,
            ) {
                TextField(
                    value = name,
                    onValueChange = onNameChange,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    placeholder = {
                        Text(
                            text = "Write your name",
                        )
                    },
                    singleLine = true,
                    colors = transparentTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester)
                )
            }
            HorizontalDivider()

            val bioFocusRequester = remember { FocusRequester() }
            EditProfileItem(
                text = "Bio",
                onChange = { bioFocusRequester.requestFocus() },
                modifier = Modifier,
            ) {
                TextField(
                    value = bio,
                    onValueChange = onBioChange,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    placeholder = {
                        Text(
                            text = "Describe yourself...",
                        )
                    },
                    singleLine = false,
                    minLines = 5,
                    suffix = {
                        Text(
                            text = bio.text.length.toString(),
                            onTextLayout = {

                            }
                        )
                    },
                    colors = transparentTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(bioFocusRequester)
                )
            }
            HorizontalDivider()

        }

        Button(
            onClick = onUpdate,
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Update")
        }
    }
}

@Composable
fun EditProfileItem(
    text: String,
    onChange: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
            val changeTextColor = Color(0xFF6BA7EC)
            Text(
                text = "Change",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = changeTextColor,
                modifier = Modifier
                    .clickable {
                        onChange()
                    }
            )
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun EditProfileScreenPreview() {
    AndroidCookbookTheme {
        EditProfileScreen(
            avatarPath = null,
            updateAvatarUri = {},
            bannerPath = null,
            updateBannerUri = {},
            name = TextFieldValue(),
            onNameChange = {},
            bio = TextFieldValue(),
            onBioChange = {},
            onBackButtonClick = {},
            onUpdate = {},
        )
    }
}

@Composable
@Preview(showBackground = false)
private fun EditProfileScreenDarkPreview() {
    AndroidCookbookTheme(darkTheme = true) {
        EditProfileScreen(
            avatarPath = null,
            updateAvatarUri = {},
            bannerPath = null,
            updateBannerUri = {},
            bio = TextFieldValue(),
            onBioChange = {},
            name = TextFieldValue(),
            onNameChange = {},
            onBackButtonClick = {},
            onUpdate = {},
        )
    }
}