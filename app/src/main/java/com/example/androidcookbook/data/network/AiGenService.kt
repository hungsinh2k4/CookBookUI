package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.aigen.AiGenFromImage
import com.example.androidcookbook.domain.model.aigen.AiResult
import com.example.androidcookbook.domain.model.aigen.UploadDataToAi
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AiGenService {
    @Multipart
    @POST("airecipe/uploadImage")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<AiGenFromImage>

    @POST("airecipe/getRecipe")
    suspend fun uploadInformation(
       @Body information: UploadDataToAi
    ): Response<AiResult>
}