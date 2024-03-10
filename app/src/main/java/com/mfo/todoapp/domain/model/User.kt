package com.mfo.todoapp.domain.model

data class User(
    val id: Long,
    val name: String,
    val lastName: String,
    val email: String) {

    fun toDomain(): User {
        return User(id, name, lastName, email)
    }
}