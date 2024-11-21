package com.example.androidcookbook.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidcookbook.ui.uistate.SignUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUiState())
    val uiState: StateFlow<SignUiState> = _uiState.asStateFlow()

    fun ChangeInOrUp(sign: Boolean) {
        _uiState.update {
            it.copy(
                isSignIn = sign
            )
        }
    }
}