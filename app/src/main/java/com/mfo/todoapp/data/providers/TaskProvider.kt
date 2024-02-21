package com.mfo.todoapp.data.providers

import com.mfo.todoapp.domain.model.Todo
import javax.inject.Inject

class TaskProvider @Inject constructor() {
    val todo = listOf<Todo>(
        //agregar aca los todo
    )
}