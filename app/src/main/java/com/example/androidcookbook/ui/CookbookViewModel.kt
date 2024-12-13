package com.example.androidcookbook.ui

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcookbook.data.providers.AccessTokenProvider
import com.example.androidcookbook.data.providers.DataStoreManager
import com.example.androidcookbook.data.providers.ThemeType
import com.example.androidcookbook.data.repositories.AuthRepository
import com.example.androidcookbook.domain.model.auth.SignInRequest
import com.example.androidcookbook.domain.model.auth.SignInResponse
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.features.auth.AuthViewModel
import com.example.androidcookbook.ui.nav.utils.sharedViewModel
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.apiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CookbookViewModel @Inject constructor(
    private val accessTokenProvider: AccessTokenProvider,
    private val dataStoreManager: DataStoreManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _uiState = MutableStateFlow(CookbookUiState())
    val uiState: StateFlow<CookbookUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    val _themeType = MutableStateFlow(ThemeType.Default)
    val themeType = _themeType.asStateFlow()

    private val _notice = MutableStateFlow(true)
    val notice = _notice.asStateFlow()



    init {
        viewModelScope.launch {
            dataStoreManager.token.combine(dataStoreManager.isLoggedIn) { token, isLoggedIn ->
                Pair(
                    token,
                    isLoggedIn
                )
            }.collect { (token, isLoggedIn) ->

                if (token != accessTokenProvider.accessToken.value && token != null) {
                    accessTokenProvider.updateAccessToken(token)
                }
                _isLoggedIn.value = isLoggedIn

                launch {
                    dataStoreManager.username.combine(dataStoreManager.password) { username, password ->
                        Pair(
                            username,
                            password
                        )
                    }.collect { (username, password) ->
                        if (user.value.id == GUEST_ID && username != null && password != null) {
                            val response = authRepository.login(SignInRequest(username, password))
                            response.onSuccess {
                                updateUser(data, username, password)
                            }
                        }
                    }
                }
            }

        }
        viewModelScope.launch {
            dataStoreManager.theme.combine(dataStoreManager.canSendNotification) { theme, canSendNotification ->
                Pair(
                    theme, canSendNotification
                )
            }.collect { (theme, canSendNotification) ->
                _themeType.value = theme
                _notice.value = canSendNotification
            }
        }
    }

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

    private val _isTopBarSet = MutableStateFlow(false)
    val isTopBarSet: StateFlow<Boolean> = _isTopBarSet.asStateFlow()

    fun setTopBarState(set: Boolean) {
        _isTopBarSet.value = set
    }



    fun updateUser(response: SignInResponse, username: String, password: String) {
        _user.update { response.user }
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.saveToken(response.accessToken)
            dataStoreManager.saveUsername(username)
            dataStoreManager.savePassword(password)
        }
        accessTokenProvider.updateAccessToken(response.accessToken)
    }


    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearLoginState()
            authRepository.sendLogOutRequest()
            _user.update { User() }
        }
    }

    fun updateUserNotice(canNotice: Boolean) {
        _notice.update { canNotice }
        viewModelScope.launch {
            dataStoreManager.saveNotification(canNotice)
        }

    }

    fun updateUserTheme(theme: ThemeType) {
        _themeType.update { theme }
        viewModelScope.launch {
            dataStoreManager.saveTheme(theme)
        }
    }

    companion object {
        var isNotificationBadgeDisplayed = MutableStateFlow(false)
        var notificationCount = MutableStateFlow(0) // TODO: Get notification count from server
            private set

        fun updateNotificationCount(count: Int) {
            notificationCount.update { count }
        }
    }
}