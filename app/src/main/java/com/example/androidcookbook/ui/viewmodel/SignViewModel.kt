package com.example.androidcookbook.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.androidcookbook.model.api.ApiResponse
import com.example.androidcookbook.model.signup.RegisterRequest
import com.example.androidcookbook.model.signup.RegisterResponse
import com.example.androidcookbook.network.authApi
import com.example.androidcookbook.ui.uistate.SignUiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun ChangeOpenDialog(open: Boolean) {
        _uiState.update {
            it.copy(
                openDialog = open
            )
        }
    }

    fun ChangeDialogMessage(message: String) {
        _uiState.update {
            it.copy(
                dialogMessage = message
            )
        }
    }

    fun SignUp(req: RegisterRequest) {
        authApi.register(req).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                ChangeOpenDialog(true)
                if (response.isSuccessful) {
                    // Trường hợp đăng ký thành công
                    val registerResponse = response.body()
                    Log.d("Register", "Success: ${registerResponse?.message}")
                    ChangeDialogMessage(registerResponse?.message.toString())
                    ChangeInOrUp(true)
                } else {
                    // Trường hợp lỗi (400, 500, etc.)
                    val errorBody = response.errorBody()?.string()
                    try {
                        // Chuyển đổi errorBody thành `RegisterResponse`
                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, RegisterResponse::class.java)
                        val message = when (val msg = errorResponse.message) {
                            is String -> msg
                            is List<*> -> msg.joinToString(", ")
                            else -> "Unknown message format"
                        }
                        Log.e("Register", "Error: $message")
                        ChangeDialogMessage(message)
                    } catch (e: Exception) {
                        Log.e("Register", "Unknown Error: ${e.message}")
                        ChangeDialogMessage(e.message.toString())
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Register", "Failure: ${t.message}")
            }
        })

    }
}