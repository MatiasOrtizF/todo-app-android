package com.mfo.todoapp.data.network

import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.data.network.response.TodoResponse
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.model.TodoShared
import com.mfo.todoapp.domain.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApiService {

    @POST("login")
    suspend fun authenticationUser(@Body loginRequest: LoginRequest): LoginResponse

    @GET("todo")
    suspend fun getAll(@Header ("Authorization") authorization: String): List<Todo>

    @POST("todo")
    suspend fun addTodo(
        @Header ("Authorization") authorization: String,
        @Query ("task") task: String
    ): TodoResponse

    @DELETE("todo/{id}")
    suspend fun deleteTodo(
        @Header ("Authorization") authorization: String,
        @Path ("id") todoId: Long
    ): Boolean

    @PUT("todo/{id}")
    suspend fun completeTodo(
        @Header ("Authorization") authorization: String,
        @Path ("id") todoId: Long
    ): TodoResponse

    @DELETE("todo/completed")
    suspend fun deleteCompletedTodos(@Header ("Authorization") authorization: String): Boolean

    @GET("todo_shared/{id}")
    suspend fun getUsersInTodoShared(
        @Header ("Authorization") authorization: String,
        @Path ("id") todoId: Long
    ): List<User>

    @POST("todo_shared/{id}")
    suspend fun addTodoShared(
        @Header ("Authorization") authorization: String,
        @Path ("id") todoId: Long,
        @Query ("userEmail") userEmail: String,
    ): TodoShared
}