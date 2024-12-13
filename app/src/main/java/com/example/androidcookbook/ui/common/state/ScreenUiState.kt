package com.example.androidcookbook.ui.common.state

sealed interface ScreenUiState<out T> {
    data object Loading : ScreenUiState<Nothing>
    data class Success<T>(val data: T) : ScreenUiState<T>
    data class Failure(val message: String) : ScreenUiState<Nothing>
    data object Guest : ScreenUiState<Nothing>
}
