package com.example.androidcookbook.domain.usecase

import android.util.Log
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): ApiResponse<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    ApiResponse.Success(it)
                }
            }
        } catch (e: Exception) {
            Log.d("UseCase", e.toString())
            ApiResponse.Failure.Exception(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}