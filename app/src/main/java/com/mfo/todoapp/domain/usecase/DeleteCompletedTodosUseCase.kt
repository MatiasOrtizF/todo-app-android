package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.domain.Repository
import javax.inject.Inject

class DeleteCompletedTodosUseCase  @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(authorization: String) = repository.deleteCompletedTodos(authorization)
}