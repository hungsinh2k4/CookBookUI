package com.example.androidcookbook.ui.features.aigen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.AiGenRepository
import com.example.androidcookbook.domain.model.aigen.AiGenFromImage
import com.example.androidcookbook.domain.model.aigen.AiResult
import com.example.androidcookbook.domain.model.aigen.UploadDataToAi
import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AiGenViewModel @Inject constructor(
    private val aiGenRepository: AiGenRepository
) : ViewModel() {

    private var _aiGenUiState = MutableStateFlow(AIGenUiState())

    val aiGenUiState: StateFlow<AIGenUiState> = _aiGenUiState.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> get() = _selectedImageUri


    private val _uploadResponse = MutableStateFlow<AiGenFromImage?>(null)
    val uploadResponse: StateFlow<AiGenFromImage?> = _uploadResponse.asStateFlow()

    private val _aiResult = MutableStateFlow<AiResult?>(null)
    val aiResult: StateFlow<AiResult?> = _aiResult.asStateFlow()

    private var isEmpty = false

    fun setIsEmpty(isEmpty: Boolean) {
        this.isEmpty = isEmpty
    }

    fun isFieldEmpty(): Boolean {
        return isEmpty
    }

    fun updateSelectedUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }


    fun deleteIngredient(index: Int) {
        _aiGenUiState.update { currentState ->
            val updatedIngredient = currentState.ingredients.toMutableList()
            updatedIngredient.removeAt(index)
            currentState.copy(ingredients = updatedIngredient)
        }
    }


    fun updateIngredientName(index: Int, updatedIngredientName: String) {

        _aiGenUiState.update { currentState ->
            val updatedIngredient = currentState.ingredients.toMutableList()
            updatedIngredient[index] = updatedIngredient[index].copy(name = updatedIngredientName)
            currentState.copy(ingredients = updatedIngredient)
        }
    }

    fun updateNote(updatedNote: String) {
        _aiGenUiState.update { currentState ->
            currentState.copy(note = updatedNote)
        }
    }

    fun updateIngredientQuantity(index: Int, updatedIngredientQuantity: String) {
        _aiGenUiState.update { currentState ->
            val updatedIngredient = currentState.ingredients.toMutableList()
            updatedIngredient[index] =
                updatedIngredient[index].copy(quantity = updatedIngredientQuantity)
            currentState.copy(ingredients = updatedIngredient)
        }
    }

    fun addEmptyIngredient() {
        _aiGenUiState.update { currentState ->
            val updatedIngredients = currentState.ingredients.toMutableList()
            updatedIngredients.add(Ingredient("", ""))
            currentState.copy(ingredients = updatedIngredients)
        }
    }

    fun addEmptyRecipe() {
        _aiGenUiState.update { currentState ->
            val updatedRecipes = currentState.recipes.toMutableList()
            updatedRecipes.add("")
            currentState.copy(
                recipes = updatedRecipes
            )
        }
    }

    fun addIngredient(ingredient: Ingredient) {
        _aiGenUiState.update { currentState ->
            val updatedIngredients = currentState.ingredients.toMutableList()
            updatedIngredients.add(ingredient)
            currentState.copy(ingredients = updatedIngredients)
        }
    }

    fun addRecipe(updatedRecipeString: String) {
        _aiGenUiState.update { currentState ->
            val updatedRecipes = currentState.recipes.toMutableList()
            updatedRecipes.add(updatedRecipeString)
            currentState.copy(
                recipes = updatedRecipes
            )
        }
    }

    fun removeRecipe(updatedRecipeString: String) {
        _aiGenUiState.update { currentState ->
            val updatedRecipes = currentState.recipes.toMutableList()
            updatedRecipes.remove(updatedRecipeString)
            currentState.copy(
                recipes = updatedRecipes
            )
        }
    }

    fun updateRecipe(index: Int, updatedRecipeString: String) {
        _aiGenUiState.update { currentState ->
            val updatedRecipes = currentState.recipes.toMutableList()
            updatedRecipes.set(index, updatedRecipeString)
            currentState.copy(
                recipes = updatedRecipes
            )
        }
    }

    fun clearIngredients() {
        _aiGenUiState.update { currentState ->
            val updatedRecipes = currentState.ingredients.toMutableList()
            updatedRecipes.clear()
            currentState.copy(
                ingredients = updatedRecipes
            )
        }
    }


    fun clearRecipes() {
        _aiGenUiState.update { currentState ->
            val updatedRecipes = currentState.recipes.toMutableList()
            updatedRecipes.clear()
            currentState.copy(
                recipes = updatedRecipes
            )
        }
    }

    fun updateIsTakingInput() {
        _aiGenUiState.update { currentState ->
            currentState.copy(
                isProcessing = false,
                isTakingInput = true,
                isDone = false,
                isDoneUploadingImage = false
            )
        }
    }


    fun updateIsProcessing() {
        _aiGenUiState.update { currentState ->
            currentState.copy(
                isProcessing = true,
                isTakingInput = false,
                isDone = false,
                isDoneUploadingImage = false
            )
        }
    }


    fun updateIsDone() {
        _aiGenUiState.update { currentState ->
            currentState.copy(
                isDone = true,
                isProcessing = false,
                isTakingInput = false,
                isDoneUploadingImage = false
            )
        }
    }

    fun updateIsDoneUploadingImage() {
        _aiGenUiState.update { currentState ->
            currentState.copy(
                isTakingInput = false,
                isDone = false,
                isProcessing = false,
                isDoneUploadingImage = true,
            )
        }
    }


    suspend fun uploadImage(imagePart: MultipartBody.Part): AiGenFromImage? {
        return try {
            val response: Response<AiGenFromImage> = aiGenRepository.uploadImage(image = imagePart)
            if (response.isSuccessful) {
                _uploadResponse.value = response.body()
                response.body()
            } else {
                Log.d("Error", "Upload failed with code: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.d("Error", "Upload Error: ${e.message}")
            null
        }
    }


    fun getAiResult() {
        viewModelScope.launch {
            updateIsProcessing()
            try {

                val response = aiGenRepository.uploadInformation(
                    UploadDataToAi(
                        ingredients = _aiGenUiState.value.ingredients,
                        recipes = _aiGenUiState.value.recipes,
                        note = _aiGenUiState.value.note
                    )
                )
                if (response.isSuccessful) {
                    if (aiGenUiState.value.isProcessing) {
                        _aiResult.value = response.body()
                        updateIsDone()
                    }
                } else {
                    updateIsTakingInput()
                    isEmpty = true
                    Log.d("Error", "Upload failed with code: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.d("Error", "Upload Error: ${e.message}")
                null
            }
        }
    }
}

