package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.domain.Repository
import com.mfo.todoapp.domain.model.Todo
import javax.inject.Inject

class PostTodoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(authorization: String, task: String) = repository.addTodo(authorization, task)
}