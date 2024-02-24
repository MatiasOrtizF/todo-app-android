package com.mfo.todoapp.ui.home

import com.mfo.todoapp.domain.model.Todo


sealed class TaskState {
    data object Loading: TaskState()
    data class Error(val error: String): TaskState()
    data class Success(val todos: MutableList<Todo>): TaskState()
}