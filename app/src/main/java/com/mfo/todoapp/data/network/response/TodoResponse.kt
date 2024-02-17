package com.mfo.todoapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.Todo

class TodoResponse (@SerializedName("todo") val todo: List<Todo>) {
    fun toDomain(): List<Todo> {
        return todo
    }
}