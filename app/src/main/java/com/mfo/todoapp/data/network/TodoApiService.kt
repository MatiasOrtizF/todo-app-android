package com.mfo.todoapp.data.network

import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.data.network.response.TodoResponse
import com.mfo.todoapp.domain.model.Todo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TodoApiService {

    @GET("todo")
    suspend fun getAll(@Header ("Authorization") authorization: String ): TodoResponse

    @POST("login")
    suspend fun authenticationUser(@Body loginRequest: LoginRequest): LoginResponse
}