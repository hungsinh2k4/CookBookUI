package com.example.androidcookbook.domain.usecase

import android.content.Context
import android.widget.Toast
import com.example.androidcookbook.data.modules.MainDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MakeToastUseCase @Inject constructor(
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
): UseCase<String, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameters: String) {
        Toast.makeText(context, parameters, Toast.LENGTH_SHORT).show()
    }
}