package com.example.androidcookbook.ui.features.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.repositories.AuthRepository
import com.example.androidcookbook.domain.model.auth.RegisterRequest
import com.example.androidcookbook.domain.model.auth.RegisterResponse
import com.example.androidcookbook.domain.model.auth.SetTokenRequest
import com.example.androidcookbook.domain.model.auth.SignInRequest
import com.example.androidcookbook.domain.model.auth.SignInResponse
import com.example.androidcookbook.domain.network.ErrorBody
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.serialization.onErrorDeserialize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    var authRequestState: MutableStateFlow<AuthRequestState> = MutableStateFlow(AuthRequestState.Idle)
        private set

    fun changeOpenDialog(open: Boolean) {
        _uiState.update {
            it.copy(
                openDialog = open
            )
        }
    }

    fun changeDialogMessage(message: String) {
        _uiState.update {
            it.copy(
                dialogMessage = message
            )
        }
    }

    private fun signInSuccess() {
        _uiState.update {
            it.copy(
                signInSuccess = true
            )
        }
    }

    fun signUp(req: RegisterRequest) {
        authRequestState.update { AuthRequestState.Loading }
        viewModelScope.launch {
            val response = authRepository.register(req)
            response.onSuccess {
                _uiState.update {
                    it.copy(
                        openDialog = true,
                        dialogMessage = data.message
                    )
                }
            }.onErrorDeserialize<RegisterResponse, ErrorBody> { errorBody ->
                _uiState.update { it.copy(dialogMessage = errorBody.message.joinToString(".\n\n")) }
            }.onException {
                when (throwable) {
                    is SocketTimeoutException -> _uiState.update { it.copy(dialogMessage = "Request timed out.\n Please try again.") }
                    is java.net.UnknownHostException -> _uiState.update { it.copy(openDialog = true, dialogMessage = "Could not establish a connection, please check your internet connection") }
                    else -> _uiState.update { it.copy(openDialog = true, dialogMessage = "An error occurred") }
                }
            }
            authRequestState.update { AuthRequestState.Idle }
        }
    }

    fun signIn(username: String, password: String, onSuccess: (SignInResponse) -> Unit) {
        _uiState.update {
            it.copy(dialogMessage = "Loading...")
        }
        authRequestState.update { AuthRequestState.Loading }
        viewModelScope.launch {
            // Send request and receive the response
            val response = authRepository.login(SignInRequest(username, password))
            response.onSuccess {
                _uiState.update {
                    it.copy(
                        openDialog = true,
                        dialogMessage = data.message,
                        signInSuccess = true
                    )
                }
                sendSetToken()
                onSuccess(data)
            }.onErrorDeserialize<SignInResponse, ErrorBody> { errorBody ->
                _uiState.update {
                    it.copy(
                        openDialog = true,
                        dialogMessage = errorBody.message.joinToString(".\n\n")
                    )
                }
            }.onException {
                when (throwable) {
                    is SocketTimeoutException -> _uiState.update {
                        it.copy(
                            openDialog = true,
                            dialogMessage = "Request timed out.\n Please try again."
                        )
                    }
                    is SocketTimeoutException -> _uiState.update { it.copy(openDialog = true, dialogMessage = "Request timed out.\n Please try again.") }
                    is java.net.UnknownHostException -> _uiState.update { it.copy(openDialog = true, dialogMessage = "Could not establish a connection, please check your internet connection") }
                    else -> _uiState.update { it.copy(openDialog = true, dialogMessage = "An error occurred") }
                }
            }
            authRequestState.update { AuthRequestState.Idle }
        }
    }

    fun sendSetToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCm registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            viewModelScope.launch {
                authRepository.sendSetTokenRequest(setTokenRequest = SetTokenRequest(token = token))
            }
            Log.d("FCM", token.toString())
        })


    }
}