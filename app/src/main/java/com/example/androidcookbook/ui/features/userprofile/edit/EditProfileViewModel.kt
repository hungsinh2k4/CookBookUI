package com.example.androidcookbook.ui.features.userprofile.edit

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.UploadRepository
import com.example.androidcookbook.data.repositories.UserRepository
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.domain.network.UpdateUserRequest
import com.example.androidcookbook.domain.usecase.CreateImageRequestBodyUseCase
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

@HiltViewModel(assistedFactory = EditProfileViewModel.EditProfileViewModelFactory::class)
class EditProfileViewModel @AssistedInject constructor(
    @Assisted private val user: User,
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository,
    private val createImageRequestBodyUseCase: CreateImageRequestBodyUseCase,
    private val makeToastUseCase: MakeToastUseCase,
) : ViewModel() {

    @AssistedFactory
    interface EditProfileViewModelFactory {
        fun create(user: User): EditProfileViewModel
    }

    var avatarUri: MutableStateFlow<Uri?> = MutableStateFlow(user.avatar?.toUri())
        private set
    var bannerUri: MutableStateFlow<Uri?> = MutableStateFlow(user.banner?.toUri())
        private set
    var name: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(user.name))
        private set
    var bio: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(user.bio?: ""))
        private set

    fun updateAvatarUri(avatar: Uri?) {
        viewModelScope.launch {
            avatarUri.update { avatar }
        }
    }

    fun updateBannerUri(banner: Uri?) {
        viewModelScope.launch {
            bannerUri.update { banner }
        }
    }

    fun updateName(name: TextFieldValue) {
        viewModelScope.launch {
            this@EditProfileViewModel.name.update { name }
        }
    }

    fun updateBio(bio: TextFieldValue) {
        viewModelScope.launch {
            this@EditProfileViewModel.bio.update { bio }
        }
    }

    fun sendUpdateUserRequest(onSuccessNavigate: () -> Unit) {
        viewModelScope.launch {
            var avatarImage: MultipartBody.Part? = null
            var bannerImage: MultipartBody.Part? = null
            avatarUri.value?.let {
                 createImageRequestBodyUseCase(it).onSuccess {
                     avatarImage = data
                }.onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to upload avatar, your profile might be missing the image")
                    }
                 }
            }

            var avatarPath: String? = null
            avatarImage?.let {
                uploadRepository.uploadImage(it).onSuccess {
                    avatarPath = data.imageURL
                }.onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to upload avatar, your profile might be missing the image")
                    }
                }
            }
            bannerUri.value?.let {
                createImageRequestBodyUseCase(it).onSuccess {
                    bannerImage = data
                }.onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to upload banner, your profile might be missing the image")
                    }
                }
            }


            var bannerPath: String? = null
            bannerImage?.let {
                uploadRepository.uploadImage(it).onSuccess {
                    bannerPath = data.imageURL
                }.onFailure {
                    viewModelScope.launch {
                        makeToastUseCase("Failed to upload banner, your profile might be missing the image")
                    }
                }
            }

            val request = UpdateUserRequest(
                avatar = avatarPath,
                banner = bannerPath,
                name = name.value.text,
                bio = bio.value.text,
            )

            userRepository.updateUser(request).onSuccess {
                viewModelScope.launch {
                    makeToastUseCase("Profile updated successfully")
                    onSuccessNavigate()
                }
            }.onFailure {
                viewModelScope.launch {
                    makeToastUseCase("Failed to update profile")
                }
            }
        }
    }
}