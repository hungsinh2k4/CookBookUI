package com.example.androidcookbook.auth

import com.example.androidcookbook.data.modules.CookbookBEModule
import com.example.androidcookbook.data.network.AuthService
import com.example.androidcookbook.data.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CookbookBEModule::class]
)
class FakeAuthRepositoryModule {

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
}