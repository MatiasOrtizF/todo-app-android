package com.mfo.todoapp.data.network

import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.data.network.response.TodoResponse
import com.mfo.todoapp.domain.model.Todo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
}