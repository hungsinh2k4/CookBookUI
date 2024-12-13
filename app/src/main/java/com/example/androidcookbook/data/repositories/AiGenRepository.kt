package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.AiGenService
import com.example.androidcookbook.domain.model.aigen.UploadDataToAi
import okhttp3.MultipartBody
import javax.inject.Inject

class AiGenRepository @Inject constructor(
    val aiGenService: AiGenService
) {
    suspend fun uploadImage(image: MultipartBody.Part) = aiGenService.uploadImage(image)

    suspend fun uploadInformation(information: UploadDataToAi) = aiGenService.uploadInformation(information)
}
