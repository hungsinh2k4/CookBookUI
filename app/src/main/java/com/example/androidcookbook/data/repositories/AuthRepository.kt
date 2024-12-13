package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.AuthService
import com.example.androidcookbook.domain.model.auth.ForgotPasswordRequest
import com.example.androidcookbook.domain.model.auth.OtpValidationRequest
import com.example.androidcookbook.domain.model.auth.OtpValidationResponse
import com.example.androidcookbook.domain.model.auth.RegisterRequest
import com.example.androidcookbook.domain.model.auth.RegisterResponse
import com.example.androidcookbook.domain.model.auth.ResetPasswordRequest
import com.example.androidcookbook.domain.model.auth.SetTokenRequest
import com.example.androidcookbook.domain.model.auth.SignInRequest
import com.example.androidcookbook.domain.model.auth.SignInResponse
import com.example.androidcookbook.domain.network.SuccessMessageBody
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {
    suspend fun register(request: RegisterRequest): ApiResponse<RegisterResponse> {
        return authService.register(request)
    }

    suspend fun login(signInRequest: SignInRequest): ApiResponse<SignInResponse> {
        val response = authService.login(signInRequest)
        return response
    }

    suspend fun sendForgotPasswordRequest(forgotPasswordRequest: ForgotPasswordRequest) =
        authService.sendForgotPasswordRequest(forgotPasswordRequest)

    suspend fun sendPasswordResetRequest(resetPasswordRequest: ResetPasswordRequest) =
        authService.sendResetPasswordRequest(resetPasswordRequest)

    suspend fun sendSetTokenRequest(setTokenRequest: SetTokenRequest): ApiResponse<Any> =
        authService.sendSetTokenRequest(setTokenRequest)

    suspend fun sendLogOutRequest() =
        authService.sendLogOutRequest()

    suspend fun sendOtpValidationRequest(request: OtpValidationRequest): ApiResponse<OtpValidationResponse> =
        authService.sendOtpValidationRequest(request)
}

