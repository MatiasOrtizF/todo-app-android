package com.mfo.todoapp.ui.home.task.adapter

import com.mfo.todoapp.domain.model.Todo

interface TaskAdapterListener {
    fun onDeleteItem(position: Int)
    fun onCompleteItem(position: Int)
    fun onAddItem(todo: Todo)
}