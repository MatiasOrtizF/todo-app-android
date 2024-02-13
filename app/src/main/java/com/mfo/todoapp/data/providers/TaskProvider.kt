package com.mfo.todoapp.data.providers

import com.mfo.todoapp.domain.model.Todo
import javax.inject.Inject

class TaskProvider @Inject constructor() {
    fun getAll(): List<Todo> {
        return listOf(

        )
    }
}