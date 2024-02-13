package com.mfo.todoapp.data

import android.util.Log
import com.mfo.todoapp.data.network.TodoApiService
import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.model.Todo
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: TodoApiService): Repository {

    /*override suspend fun getAll(authorizaiton: String): Todo {
        runCatching { apiService.getAll(authorizaiton) }
            .onSuccess { return it }
            .onFailure { Log.i("mfo", "Error ocurred ${it.message}") }
    }*/

    override suspend fun authenticationUser(loginRequest: LoginRequest): LoginModel? {
        runCatching { apiService.authenticationUser(loginRequest) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("mfo", "Error ocurred ${it.message}") }
        return null
    }
}