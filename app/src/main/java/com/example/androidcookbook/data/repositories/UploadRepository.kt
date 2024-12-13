package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.UploadService
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadRepository @Inject constructor(
    private val uploadService: UploadService
) {
    suspend fun uploadImage(image: MultipartBody.Part) = uploadService.uploadImage(image)
}