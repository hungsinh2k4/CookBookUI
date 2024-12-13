package com.example.androidcookbook.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.androidcookbook.data.modules.IoDispatcher
import com.skydoves.sandwich.mapSuccess
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateImageRequestBodyUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): UseCase<Uri, MultipartBody.Part?>(coroutineDispatcher) {
    override suspend fun execute(parameters: Uri): MultipartBody.Part? {
        val file = getFileFromUri(parameters)
        return file?.let {
            val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", it.name, requestFile)
        }
    }

    private fun getFileFromUri(uri: Uri?): File? {
        return try {
            val inputStream = uri?.let { context.contentResolver.openInputStream(it) } ?: return null
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

/**
 * @deprecated Use CreateImageRequestBodyUseCase instead
 */
@Deprecated(
    message = "Use CreateImageRequestBodyUseCase instead",
    replaceWith = ReplaceWith("CreateImageRequestBodyUseCase")
)
suspend fun createImageRequestBody(context: Context, imageUri: Uri): MultipartBody.Part? {
    var result: MultipartBody.Part? = null
     CreateImageRequestBodyUseCase(context, Dispatchers.IO).invoke(imageUri).onSuccess {
         result = data
    }
    return result
}

