package com.example.androidcookbook.data.providers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenProvider @Inject constructor() {
    private val _accessToken = MutableStateFlow("")

    val accessToken: StateFlow<String> get() = _accessToken

    fun updateAccessToken(token: String) {
        _accessToken.value = token
    }
}
