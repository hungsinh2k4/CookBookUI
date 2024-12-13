package com.example.androidcookbook

import com.example.androidcookbook.data.modules.CookbookBEModule
import com.example.androidcookbook.data.modules.CookbookBEModule.CookbookRetrofit
import com.example.androidcookbook.data.network.AiGenService
import com.example.androidcookbook.data.network.AllSearcherService
import com.example.androidcookbook.data.network.AuthService
import com.example.androidcookbook.data.network.NewsfeedService
import com.example.androidcookbook.data.network.NotificationService
import com.example.androidcookbook.data.network.PostService
import com.example.androidcookbook.data.network.UploadService
import com.example.androidcookbook.data.network.UserService
import com.example.androidcookbook.data.repositories.AiGenRepository
import com.example.androidcookbook.data.repositories.AllSearcherRepository
import com.example.androidcookbook.data.repositories.AuthRepository
import com.example.androidcookbook.data.repositories.NewsfeedRepository
import com.example.androidcookbook.data.repositories.NotificationRepository
import com.example.androidcookbook.data.repositories.PostRepository
import com.example.androidcookbook.data.repositories.UploadRepository
import com.example.androidcookbook.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CookbookBEModule::class]
)
class FakeCookbookBEModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepository(authService)
    }

    @Singleton
    @Provides
    fun provideAuthService(): AuthService {
        return mockk(relaxed = true) // Creates a mock with relaxed responses
    }

    @Provides
    @Singleton
    fun provideUserService(): UserService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository =
        UserRepository(userService)

    @Provides
    @Singleton
    fun providePostService(): PostService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun providePostRepository(postService: PostService) =
        PostRepository(postService)

    @Provides
    @Singleton
    fun provideAiGenService(): AiGenService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideAiGenRepository(aiGenService: AiGenService): AiGenRepository =
        AiGenRepository(aiGenService)

    @Provides
    @Singleton
    fun provideNewsfeedService(): NewsfeedService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideNewsfeedRepository(newsfeedService: NewsfeedService): NewsfeedRepository =
        NewsfeedRepository(newsfeedService)

    @Provides
    @Singleton
    fun provideUploadService(): UploadService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideUploadRepository(uploadService: UploadService): UploadRepository =
        UploadRepository(uploadService)

    @Provides
    @Singleton
    fun provideAllSearcherService(): AllSearcherService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideAllSearcherRepository(allSearcherService: AllSearcherService): AllSearcherRepository =
        AllSearcherRepository(allSearcherService)

    @Provides
    @Singleton
    fun provideNotificationService(): NotificationService =
        mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationService: NotificationService): NotificationRepository =
        NotificationRepository(notificationService)
}
