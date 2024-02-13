package com.mfo.todoapp.domain

import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.model.Todo
import retrofit2.Call

interface Repository {
    //suspend fun getAll(authorization: String): Todo
    suspend fun authenticationUser(loginRequest: LoginRequest): LoginModel?
}