package com.example.androidcookbook.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CookbookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CookbookUiState())
    val uiState: StateFlow<CookbookUiState> = _uiState.asStateFlow()

    fun updateCanNavigateBack(updatedCanNavigateBack: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                canNavigateBack = updatedCanNavigateBack
            )
        }
    }

    fun updateTopBarState(topBarState: CookbookUiState.TopBarState) {
        _uiState.update { it.copy(topBarState = topBarState) }
    }

    fun updateBottomBarState(bottomBarState: CookbookUiState.BottomBarState) {
        _uiState.update { it.copy(bottomBarState = bottomBarState) }
    }
}