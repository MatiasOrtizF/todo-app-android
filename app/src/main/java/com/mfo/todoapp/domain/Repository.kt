package com.mfo.todoapp.domain

import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.data.network.response.TodoResponse
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.model.TodoShared
import com.mfo.todoapp.domain.model.User
import retrofit2.Call

interface Repository {
    //suspend fun getAll(authorization: String): Todo
    suspend fun authenticationUser(loginRequest: LoginRequest): LoginModel?
    suspend fun putTokenValue(token: String)
    suspend fun getTokenValue(token: String): String?
    suspend fun getAll(authorization: String): List<Todo>?
    suspend fun addTodo(authorization: String, task: String): Todo?
    suspend fun deleteTodo(authorization: String, todoId: Long): Boolean
    suspend fun completeTodo(authorization: String, todoId: Long): Todo?
    suspend fun deleteCompletedTodos(authorization: String): Boolean
    suspend fun getUsersInTodoShared(authorization: String, todoId: Long): List<User>?
    suspend fun addTodoShared(authorization: String, todoId: Long, userEmail: String): TodoShared?
}