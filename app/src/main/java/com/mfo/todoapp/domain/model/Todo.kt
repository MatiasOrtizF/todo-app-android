package com.mfo.todoapp.domain.model

import com.google.gson.annotations.SerializedName

data class Todo (
    val id: Long,
    val task: String,
    var completed: Boolean,
    val user: User) {

    fun toDomain(): Todo {
        return Todo(id, task, completed, user)
    }
}