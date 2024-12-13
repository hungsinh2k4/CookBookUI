package com.example.androidcookbook.data.modules

import com.example.androidcookbook.data.network.AiGenService
import com.example.androidcookbook.data.network.AllSearcherService
import com.example.androidcookbook.data.network.AuthService
import com.example.androidcookbook.data.network.NewsfeedService
import com.example.androidcookbook.data.network.NotificationService
import com.example.androidcookbook.data.network.PostService
import com.example.androidcookbook.data.network.UploadService
import com.example.androidcookbook.data.network.UserService
import com.example.androidcookbook.data.providers.AccessTokenProvider
import com.example.androidcookbook.data.repositories.AiGenRepository
import com.example.androidcookbook.data.repositories.AllSearcherRepository
import com.example.androidcookbook.data.repositories.AuthRepository
import com.example.androidcookbook.data.repositories.NewsfeedRepository
import com.example.androidcookbook.data.repositories.NotificationRepository
import com.example.androidcookbook.data.repositories.PostRepository
import com.example.androidcookbook.data.repositories.UploadRepository
import com.example.androidcookbook.data.repositories.UserRepository
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CookbookBEModule {

    private const val DIGITAL_OCEAN_API = "https://octopus-app-lvf9o.ondigitalocean.app/"
    private const val COOKBOOK_BE = "https://cookbook-f98z.onrender.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CookbookRetrofit

    @Provides
    @Singleton
    fun provideOkHttpClient(accessTokenProvider: AccessTokenProvider) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = accessTokenProvider.accessToken.value
            val request = chain.request().newBuilder()
                .apply {
                    if (token.isNotEmpty()) {
                        addHeader("Authorization", "Bearer $token") // Attach token
                    }
                }

                .build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    @CookbookRetrofit
    @Provides
    @Singleton
    fun provideCookBE(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(DIGITAL_OCEAN_API)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthService(@CookbookRetrofit retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository =
        AuthRepository(authService)

    @Provides
    @Singleton
    fun provideUserService(@CookbookRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService) =
        UserRepository(userService)

    @Provides
    @Singleton
    fun providePostService(@CookbookRetrofit retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)

    @Provides
    @Singleton
    fun providePostRepository(postService: PostService) =
        PostRepository(postService)

    @Provides
    @Singleton
    fun provideAiGenService(@CookbookRetrofit retrofit: Retrofit): AiGenService =
        retrofit.create(AiGenService::class.java)

    @Provides
    @Singleton
    fun provideAiGenRepository(aiGenService: AiGenService): AiGenRepository =
        AiGenRepository(aiGenService)

    @Provides
    @Singleton
    fun provideNewsfeedService(@CookbookRetrofit retrofit: Retrofit): NewsfeedService =
        retrofit.create(NewsfeedService::class.java)

    @Provides
    @Singleton
    fun provideNewsfeedRepository(newsfeedService: NewsfeedService): NewsfeedRepository =
        NewsfeedRepository(newsfeedService)

    @Provides
    @Singleton
    fun provideUploadService(@CookbookRetrofit retrofit: Retrofit): UploadService =
        retrofit.create(UploadService::class.java)

    @Provides
    @Singleton
    fun provideUploadRepository(uploadService: UploadService): UploadRepository =
        UploadRepository(uploadService)

    @Provides
    @Singleton
    fun provideAllSearcherService(@CookbookRetrofit retrofit: Retrofit): AllSearcherService =
        retrofit.create(AllSearcherService::class.java)

    @Provides
    @Singleton
    fun provideAllSearcherRepository(allSearcherService: AllSearcherService): AllSearcherRepository =
        AllSearcherRepository(allSearcherService)

    @Provides
    @Singleton
    fun provideNotificationService(@CookbookRetrofit retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationService: NotificationService): NotificationRepository =
        NotificationRepository(notificationService)
}
