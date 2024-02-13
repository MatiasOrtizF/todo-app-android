package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.data.network.response.LoginResponse
import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.LoginRequest
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(loginRequest: LoginRequest) = repository.authenticationUser(loginRequest)
}