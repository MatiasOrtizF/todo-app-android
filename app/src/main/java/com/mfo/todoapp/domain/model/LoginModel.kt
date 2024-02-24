package com.mfo.todoapp.domain.model

data class LoginModel(
    val token: String,
    val user: User) {
}