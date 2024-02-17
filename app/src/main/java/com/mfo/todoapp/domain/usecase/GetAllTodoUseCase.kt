package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.LoginRequest
import javax.inject.Inject

class GetAllTodoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(authorization: String) = repository.getTokenValue(authorization)
}