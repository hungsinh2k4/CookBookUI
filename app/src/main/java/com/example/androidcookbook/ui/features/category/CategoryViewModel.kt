package com.example.androidcookbook.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private var _categoryUiState: MutableStateFlow<CategoryUiState> = MutableStateFlow(CategoryUiState.Loading)
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categoryUiState.update { CategoryUiState.Loading }
            try {
                _categoryUiState.update{ CategoryUiState.Success(categoriesRepository.getCategories()) }
            } catch (e: IOException) {
                _categoryUiState.update{ CategoryUiState.Error }
            } catch (e: HttpException) {
                _categoryUiState.update{ CategoryUiState.Error }
            }
        }
    }

    fun refresh() {
        getCategories()
    }
}