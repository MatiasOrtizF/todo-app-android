package com.mfo.todoapp.ui.home


sealed class TaskState {
    data object Loading: TaskState()
    data class Error(val error: String): TaskState()
    data class Success(val data: String): TaskState()
}