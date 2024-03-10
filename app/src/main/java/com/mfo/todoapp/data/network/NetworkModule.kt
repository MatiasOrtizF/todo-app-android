package com.mfo.todoapp.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mfo.todoapp.data.RepositoryImpl
import com.mfo.todoapp.data.preferences.Preferences
import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoApiService(retrofit: Retrofit): TodoApiService {
        return retrofit.create(TodoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: TodoApiService, preferences: Preferences): Repository {
        return RepositoryImpl(apiService, preferences)
    }
}