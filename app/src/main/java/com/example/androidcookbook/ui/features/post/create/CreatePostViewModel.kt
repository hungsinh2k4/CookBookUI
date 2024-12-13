package com.example.androidcookbook.ui.features.post.create

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.PostRepository
import com.example.androidcookbook.data.repositories.UploadRepository
import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.post.PostCreateRequest
import com.example.androidcookbook.domain.usecase.CreateImageRequestBodyUseCase
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.skydoves.sandwich.message
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

@HiltViewModel(assistedFactory = CreatePostViewModel.CreatePostViewModelFactory::class)
class CreatePostViewModel @AssistedInject constructor(
    @Assisted private val post: Post,
    private val postRepository: PostRepository,
    private val uploadRepository: UploadRepository,
    private val createImageRequestBody: CreateImageRequestBodyUseCase,
    private val makeToastUseCase: MakeToastUseCase,
) : ViewModel() {

    @AssistedFactory
    interface CreatePostViewModelFactory {
        fun create(post: Post): CreatePostViewModel
    }

    val showErrorMessage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val cookTime = MutableStateFlow<String>("")
    val postImageUri = MutableStateFlow<Uri?>(post.mainImage?.toUri())
    val postTitle = MutableStateFlow(post.title)
    val postBody = MutableStateFlow(post.description)
    val ingredients: MutableStateFlow<List<Ingredient>> = MutableStateFlow(post.ingredient ?: emptyList())
    val recipe: MutableStateFlow<List<String>> = MutableStateFlow(post.steps ?: emptyList())

    val isAddStepDialogOpen = MutableStateFlow(false)
    val isAddIngredientDialogOpen = MutableStateFlow(false)
    val updateStepDialogState = MutableStateFlow<UpdateStepDialogState>(UpdateStepDialogState.Closed)
    val updateIngredientDialogState = MutableStateFlow<UpdateIngredientDialogState>(UpdateIngredientDialogState.Closed)

    fun updateCookTime(time: String) {
        cookTime.update { time }
    }

    fun updatePostTitle(title: String) {
        postTitle.update { title }
        showErrorMessage.update { false }
    }

    fun updatePostBody(body: String) {
        postBody.update { body }
        showErrorMessage.update { false }
    }

    fun updatePostImageUri(uri: Uri?) {
        postImageUri.update { uri }
    }

    fun openAddStepDialog() {
        isAddStepDialogOpen.update { true }
    }

    fun openAddIngredientDialog() {
        isAddIngredientDialogOpen.update { true }
    }

    fun closeDialog() {
        isAddStepDialogOpen.update { false }
        isAddIngredientDialogOpen.update { false }
        updateStepDialogState.update { UpdateStepDialogState.Closed }
        updateIngredientDialogState.update { UpdateIngredientDialogState.Closed }
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredients.update {
            it + ingredient
        }
    }

    fun addStepToRecipe(step: String) {
        recipe.update {
            it + step
        }
    }

    fun deleteStep(index: Int) {
        recipe.update {
            it.filterIndexed { i, _ -> i != index }
        }
    }

    fun deleteIngredient(index: Int) {
        ingredients.update {
            it.filterIndexed { i, _ -> i != index }
        }
    }

    fun updateStep(index: Int, step: String) {
        recipe.update {
            val list = it.toMutableList()
            list[index] = step
            list
        }

    }

    fun openUpdateStep(index: Int) {
        updateStepDialogState.update { UpdateStepDialogState.Open(index, recipe.value[index]) }
    }

    fun updateIngredient(index: Int, ingredient: Ingredient) {
        ingredients.update {
            val list = it.toMutableList()
            list[index] = ingredient
            list
        }
    }

    fun openUpdateIngredient(index: Int) {
        updateIngredientDialogState.update { UpdateIngredientDialogState.Open(index, ingredients.value[index]) }
    }

    private suspend fun uploadImage(): String? {
        var mainImage: String? = null
        var imageRequestBody: MultipartBody.Part? = null
        postImageUri.value?.let { createImageRequestBody.invoke(it) }?.onSuccess {
            imageRequestBody = data
        }
        val imageResponse = imageRequestBody?.let { uploadRepository.uploadImage(it) }
        imageResponse?.onSuccess {
            mainImage = data.imageURL
        }?.onFailure {
            Log.e("CreatePostViewModel", message())
            viewModelScope.launch {
                makeToastUseCase("Failed to upload image, your post might be missing the image")
            }
        }
        return mainImage
    }

    fun createPost(onSuccessNavigate: (Post) -> Unit) {
        if (postTitle.value.isEmpty() || postBody.value.isEmpty()) {
            showErrorMessage.update { true }
            return
        }
        viewModelScope.launch {
            val mainImage = uploadImage()
            val response = postRepository.createPost(
                PostCreateRequest(
                    title = postTitle.value,
                    description = postBody.value,
                    mainImage = mainImage,
                    cookTime = null,
                    ingredient = ingredients.value,
                    steps = recipe.value,
                )
            )
            response.onSuccess {
                onSuccessNavigate(data.post)
            }.onFailure {
                viewModelScope.launch {
                    makeToastUseCase("Failed to create post")
                }
            }
        }
    }

    fun updatePost(onSuccessNavigate: (Post) -> Unit) {
        viewModelScope.launch {
            val mainImage = uploadImage()
            val response = postRepository.updatePost(
                post.id,
                PostCreateRequest(
                    title = postTitle.value,
                    description = postBody.value,
                    mainImage = mainImage,
                    cookTime = null,
                    ingredient = null,
                    steps = null,
                )
            )
            response.onSuccess {
                onSuccessNavigate(data.post)
            }.onFailure {
                viewModelScope.launch {
                    makeToastUseCase("Failed to update post")
                }
            }
        }
    }


}

sealed interface UpdateIngredientDialogState {
    data object Closed : UpdateIngredientDialogState
    data class Open(val index: Int, val ingredient: Ingredient) : UpdateIngredientDialogState
}

sealed interface UpdateStepDialogState {
    data object Closed : UpdateStepDialogState
    data class Open(val index: Int, val step: String) : UpdateStepDialogState
}
