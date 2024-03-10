package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.domain.Repository
import javax.inject.Inject

class GetUsersInTodoSharedUseCase  @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(authorization: String, todoId: Long) = repository.getUsersInTodoShared(authorization, todoId)
}