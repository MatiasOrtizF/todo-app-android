package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.Todo
import javax.inject.Inject

class PostTodoSharedUseCase @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(authorization: String, todoId: Long, userEmail: String) = repository.addTodoShared(authorization, todoId, userEmail)
}