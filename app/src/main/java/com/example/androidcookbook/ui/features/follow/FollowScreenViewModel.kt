package com.example.androidcookbook.ui.features.follow

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androidcookbook.domain.model.user.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = FollowScreenViewModel.FollowScreenViewModelFactory::class)
class FollowScreenViewModel @AssistedInject constructor(
    @Assisted _screenType: FollowListScreenType,
) : ViewModel() {
    var screenType: MutableStateFlow<FollowListScreenType> = MutableStateFlow(_screenType)
        private set

    @AssistedFactory
    interface FollowScreenViewModelFactory {
        fun create(
            @Assisted _screenType: FollowListScreenType,
        ): FollowScreenViewModel
    }

    fun setScreenType(type: FollowListScreenType) {
        screenType.update { type }
    }

}