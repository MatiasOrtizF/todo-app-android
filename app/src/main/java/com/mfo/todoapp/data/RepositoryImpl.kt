package com.mfo.todoapp.data

import android.util.Log
import com.mfo.todoapp.data.network.TodoApiService
import com.mfo.todoapp.data.network.response.TodoResponse
import com.mfo.todoapp.data.preferences.Preferences
import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.model.User
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: TodoApiService, private val preferences: Preferences): Repository {

    override suspend fun authenticationUser(loginRequest: LoginRequest): LoginModel? {
        runCatching { apiService.authenticationUser(loginRequest) }
            .onSuccess { return it.toDomain() }
            .onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        return null
    }

    override suspend fun getAll(authorization: String): List<Todo>? {
        runCatching {
            val todos = apiService.getAll(authorization)
            todos.map { it.toDomain() }
        }
            .onSuccess { todos -> return todos }
            .onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        return null
    }

    override suspend fun addTodo(authorization: String, task: String): Todo? {
        runCatching { apiService.addTodo(authorization, task) }
            .onSuccess { it.toDomain() }
            .onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        return null
    }

    override suspend fun deleteTodo(authorization: String, todoId: Long): Boolean {
        return runCatching {
            apiService.deleteTodo(authorization, todoId)
        }.fold(
            onSuccess = {
                true
            },
            onFailure = { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        )
    }

    override suspend fun completeTodo(authorization: String, todoId: Long): Todo? {
        runCatching { apiService.completeTodo(authorization, todoId) }
            .onSuccess { it.toDomain() }
            .onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        return null
    }

    override suspend fun deleteCompletedTodos(authorization: String): Boolean {
        return runCatching {
            apiService.deleteCompletedTodos(authorization)
        }.fold(
            onSuccess = {
                true
            },
            onFailure = { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        )
    }

    override suspend fun getUsersInTodoShared(authorization: String, todoId: Long): List<User>? {
        runCatching {
            val users = apiService.getUsersInTodoShared(authorization, todoId)
            users.map { it.toDomain() }
        }
            .onSuccess { users -> return users }
            .onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> throwable.response()?.errorBody()?.string()
                    else -> null
                } ?: "An error ocurred: ${throwable.message}"
                Log.i("mfo", "Error occurred: $errorMessage")
                throw Exception(errorMessage)
            }
        return null
    }

    override suspend fun putTokenValue(token: String) {
        preferences.putTokenValue(token)
    }

    override suspend fun getTokenValue(token: String): String? {
        return preferences.getTokenValue(token)
    }
}