package com.example.androidcookbook.ui.nav.dest

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcookbook.data.repositories.NotificationRepository
import com.example.androidcookbook.domain.model.notification.Notification
import com.example.androidcookbook.domain.usecase.MakeToastUseCase
import com.example.androidcookbook.ui.common.state.ScreenUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val makeToastUseCase: MakeToastUseCase,
) : ViewModel() {

    var isRefreshing = MutableStateFlow(false)
        private set
    var notificationUiState: MutableStateFlow<ScreenUiState<List<Notification>>> = MutableStateFlow(ScreenUiState.Loading)
        private set
    private var page: Int = 1

    init {
        refresh()
    }

    private fun getNotifications(page: Int) {
        viewModelScope.launch {
            val response = notificationRepository.getNotifications(page)
            response.onSuccess {
                Log.d("NotificationViewModel", "getNotifications: $data")
                notificationUiState.update { ScreenUiState.Success(data.notifications) }
            }.onFailure {
                notificationUiState.update { ScreenUiState.Failure(message()) }
            }
        }
    }

    fun loadMore() {
        page++
        viewModelScope.launch {
            getNotifications(page)
        }
    }

    private suspend fun clearNotification(notificationId: Int) {
        val response = notificationRepository.clearNotification(notificationId)
        response.onFailure {
            viewModelScope.launch {
                makeToastUseCase(message())
            }
        }
    }

    fun clearAllNotifications() {
        if (notificationUiState.value is ScreenUiState.Success) {
            val notifications = (notificationUiState.value as ScreenUiState.Success<List<Notification>>).data
            for (notification in notifications) {
                viewModelScope.launch {
                    clearNotification(notification.id)
                }
            }
        }
    }

    fun updateEmpty() {
        notificationUiState.update { ScreenUiState.Success(emptyList()) }
    }

    fun markRead(notificationId: Int) {
        viewModelScope.launch {
            val response = notificationRepository.readNotification(notificationId)


        }
    }


    fun refresh() {
        page = 1
        isRefreshing.update { true }
        viewModelScope.launch {
            getNotifications(page)
        }.invokeOnCompletion {
            isRefreshing.update { false }
        }
    }


}