package com.mfo.todoapp.data.network

import com.mfo.todoapp.data.RepositoryImpl
import com.mfo.todoapp.data.preferences.Preferences
import com.mfo.todoapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.65.1:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideTodoApiService(retrofit: Retrofit): TodoApiService {
        return retrofit.create(TodoApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: TodoApiService, preferences: Preferences): Repository {
        return RepositoryImpl(apiService, preferences)
    }
}