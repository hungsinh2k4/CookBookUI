package com.example.androidcookbook.ui.features.category

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcookbook.data.repositories.CategoriesRepository
import com.example.androidcookbook.domain.model.category.Category
import com.example.androidcookbook.domain.model.recipe.DisplayRecipeDetail
import com.example.androidcookbook.domain.model.recipe.Recipe
import com.example.androidcookbook.domain.model.recipe.RecipeDetails
import com.example.androidcookbook.domain.model.recipe.RecipeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    var isRefreshing = MutableStateFlow(false)
        private set

    private val _isTopBarSet = MutableStateFlow(false)
    val isTopBarSet: StateFlow<Boolean> = _isTopBarSet.asStateFlow()

    fun setTopBarState(set: Boolean) {
        _isTopBarSet.value = set
    }

    private var _categoryUiState: MutableStateFlow<CategoryUiState> =
        MutableStateFlow(CategoryUiState())
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState


    var recipeList: RecipeList = RecipeList(mutableListOf())
    var categories: MutableList<Category> = mutableListOf()
    var randomMeals: MutableList<Recipe> = mutableListOf()
    var currentCategory: String = ""
    var currentRecipeDetail = DisplayRecipeDetail()

    init {
        getCategoriesAndRandomMeals()
    }

    fun getRecipesDetails(idMeal: Int) {
        updateUiStateLoadingRecipeDetail()
        viewModelScope.launch {

            val result = categoriesRepository.getMealById(idMeal).meals.first()



            currentRecipeDetail = DisplayRecipeDetail(
                idMeal = result.idMeal,
                strMeal = result.strMeal,
                strCategory = result.strCategory,
                strInstructions = result.strInstructions,
                strArea = result.strArea,
                strMealThumb = result.strMealThumb,
                strSource = result.strSource
            )

            (1..20).forEach { index ->
                val ingredient = result::class.java.getDeclaredField("strIngredient$index").apply { isAccessible = true }.get(result) as? String
                val measure = result::class.java.getDeclaredField("strMeasure$index").apply { isAccessible = true }.get(result) as? String
                if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                    currentRecipeDetail.ingredients.add("$ingredient - $measure")
                }
            }
            if(categoryUiState.value.isLoadingRecipeDetails) {
                updateUiStateRecipeDetail()
            }

        }

    }

    fun updateUiStateRecipeDetail() {
        _categoryUiState.update { currentState ->
            currentState.copy(
                isCategory = false,
                isListDetail = false,
                isRecipeDetail = true,
                isLoading = false,
                isError = false,
                isLoadingRecipeDetails = false,
                isLoadingRecipeList = false
            )
        }
    }

    fun updateUiStateCategory() {
        _categoryUiState.update { currentState ->
            currentState.copy(
                isCategory = true,
                isListDetail = false,
                isRecipeDetail = false,
                isLoading = false,
                isError = false,
                isLoadingRecipeDetails = false,
                isLoadingRecipeList = false
            )
        }

    }

    fun updateUiStateCategoryDetail() {
        _categoryUiState.update { currentState ->
            currentState.copy(
                isCategory = false,
                isListDetail = true,
                isRecipeDetail = false,
                isLoading = false,
                isError = false,
                isLoadingRecipeDetails = false,
                isLoadingRecipeList = false
            )
        }
    }

    fun updateUiStateLoading() {
        _categoryUiState.update { currentState ->
            currentState.copy(
                isCategory = false,
                isListDetail = false,
                isRecipeDetail = false,
                isLoading = true,
                isError = false,
                isLoadingRecipeDetails = false,
                isLoadingRecipeList = false
            )
        }
    }

    fun updateUiStateLoadingRecipeList() {
        _categoryUiState.update { currentState ->
            currentState.copy(
                isCategory = false,
                isListDetail = false,
                isRecipeDetail = false,
                isLoading = false,
                isError = false,
                isLoadingRecipeDetails = false,
                isLoadingRecipeList = true
            )
        }
    }

    fun updateUiStateLoadingRecipeDetail() {
        _categoryUiState.update { currentState ->
            currentState.copy(
                isCategory = false,
                isListDetail = false,
                isRecipeDetail = false,
                isLoading = false,
                isError = false,
                isLoadingRecipeDetails = true,
                isLoadingRecipeList = false
            )
        }
    }



    fun getListDetail(category: String) {
        updateUiStateLoadingRecipeList()
        viewModelScope.launch {
            try {
                currentCategory = category
                recipeList = categoriesRepository.getMealByCategories(category)
                if(categoryUiState.value.isLoadingRecipeList) {
                    updateUiStateCategoryDetail()
                }
            } catch (e: IOException) {
                _categoryUiState.update { currentState ->
                    currentState.copy(
                        isCategory = false,
                        isListDetail = false,
                        isRecipeDetail = false,
                        isLoading = false,
                        isError = true,
                        isLoadingRecipeDetails = false,
                        isLoadingRecipeList = false
                    )
                }
            } catch (e: HttpException) {
                _categoryUiState.update { currentState ->
                    currentState.copy(
                        isCategory = false,
                        isListDetail = false,
                        isRecipeDetail = false,
                        isLoading = false,
                        isError = true,
                        isLoadingRecipeDetails = false,
                        isLoadingRecipeList = false
                    )
                }
            }
        }
    }


    private fun getCategoriesAndRandomMeals() {
        viewModelScope.launch {
            isRefreshing.update { true }
            updateUiStateLoading()
            try {
                val recipeList: MutableList<Recipe> = mutableListOf()
                val categoryList: MutableList<Category> = categoriesRepository.getCategories()

                coroutineScope {
                    val mealRequests = List(8) {
                        async {
                            categoriesRepository.getRandomMeal().meals.firstOrNull()
                        }
                    }
                    mealRequests.forEach { request ->
                        request.await()?.let { meal ->
                            recipeList.add(meal)
                        }
                    }
                }



                categories = categoryList
                randomMeals = recipeList



                updateUiStateCategory()
            } catch (e: IOException) {
                _categoryUiState.update { currentState ->
                    currentState.copy(
                        isCategory = false,
                        isListDetail = false,
                        isRecipeDetail = false,
                        isLoading = false,
                        isError = true,
                        isLoadingRecipeDetails = false,
                        isLoadingRecipeList = false
                    )
                }
            } catch (e: HttpException) {
                _categoryUiState.update { currentState ->
                    currentState.copy(
                        isCategory = false,
                        isListDetail = false,
                        isRecipeDetail = false,
                        isLoading = false,
                        isError = true,
                        isLoadingRecipeDetails = false,
                        isLoadingRecipeList = false
                    )
                }
            }
        }.invokeOnCompletion {
            isRefreshing.update { false }
        }
    }


    fun refresh() {
        getCategoriesAndRandomMeals()
    }
}