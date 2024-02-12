package com.mfo.todoapp.domain.model

import com.google.gson.annotations.SerializedName

data class Todo (
    @SerializedName("id") val id: Long,
    @SerializedName("task") val task: String,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("user") val user: User) {
}