package com.example.androidcookbook.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidcookbook.data.CookbookRepository
import com.example.androidcookbook.ui.uistate.CookbookUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



class CookbookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CookbookUiState())
    val uiState: StateFlow<CookbookUiState> = _uiState.asStateFlow()

    fun updateSearchQuery(updatedSearchQuery: String) {
       _uiState.update { currentState ->
           currentState.copy(
               searchQuery = updatedSearchQuery
           )
       }
    }
}