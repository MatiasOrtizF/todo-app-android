package com.mfo.todoapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.model.User

data class TodoResponse (@SerializedName("id") val id: Long, @SerializedName("task") val task: String, @SerializedName("completed") val completed: Boolean, @SerializedName("user")  val user: User) {
    fun toDomain(): Todo {
        return Todo(
            id = id,
            task = task,
            completed = completed,
            user = user)
    }
}