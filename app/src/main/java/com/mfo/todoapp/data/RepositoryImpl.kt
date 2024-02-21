package com.mfo.todoapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.mfo.todoapp.data.network.TodoApiService
import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.data.network.response.TodoResponse
import com.mfo.todoapp.data.preferences.Preferences
import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.model.Todo
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: TodoApiService, private val preferences: Preferences): Repository {

    override suspend fun getAll(authorization: String): List<Todo>? {
        runCatching {
            val todos = apiService.getAll(authorization)
            todos.map { it.toDomain() }
        }
            .onSuccess { todos -> return todos }
            .onFailure { Log.i("mfo", "Error ocurred ${it.message}") }
        return null
    }

    override suspend fun authenticationUser(loginRequest: LoginRequest): LoginModel? {
        runCatching { apiService.authenticationUser(loginRequest) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("mfo", "Error ocurred ${it.message}") }
        return null
    }

    override suspend fun putTokenValue(token: String) {
        preferences.putTokenValue(token)
    }

    override suspend fun getTokenValue(token: String): String? {
        return preferences.getTokenValue(token)
    }
}