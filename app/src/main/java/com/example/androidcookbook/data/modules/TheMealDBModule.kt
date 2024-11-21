package com.example.androidcookbook.data.modules

import com.example.androidcookbook.data.network.CategoriesService
import com.example.androidcookbook.data.network.SearchService
import com.example.androidcookbook.data.repositories.CategoriesRepository
import com.example.androidcookbook.data.repositories.SearchRepository
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TheMealDBModule {

    private const val THE_MEAL_DB = "https://www.themealdb.com/api/json/v1/1/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TheMealDBRetrofit

    @TheMealDBRetrofit
    @Provides
    @Singleton
    fun provideTheMealDB(): Retrofit = Retrofit.Builder()
        .baseUrl(THE_MEAL_DB)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCategoriesService(@TheMealDBRetrofit retrofit: Retrofit): CategoriesService =
        retrofit.create(CategoriesService::class.java)

    @Provides
    @Singleton
    fun provideCategoriesRepository(categoriesService: CategoriesService): CategoriesRepository =
        CategoriesRepository(categoriesService)

    @Provides
    @Singleton
    fun provideSearchService(@TheMealDBRetrofit retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideSearchRepository(searchService: SearchService): SearchRepository =
        SearchRepository(searchService)
}