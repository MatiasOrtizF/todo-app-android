package com.mfo.todoapp.domain.model

data class TodoShared (
    val id: Long,
    val user: User,
    val todo: Todo) {

    fun toDomain(): TodoShared {
        return TodoShared(id, user, todo)
    }
}