package com.example.androidcookbook.ui.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.SearchRepository
import com.example.androidcookbook.domain.model.recipe.RecipeList
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun search(searchQuery: String) {
        viewModelScope.launch {
            val response: ApiResponse<RecipeList?> = searchRepository.search(searchQuery)
            response.onSuccess {
                _uiState.update {
                    it.copy(result = data?.meals.toString())
                }
            }.onFailure {
                _uiState.update { it.copy(result = "Failed to fetch data") }
            }
        }
    }
}