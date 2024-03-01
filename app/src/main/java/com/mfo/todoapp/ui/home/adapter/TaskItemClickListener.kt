package com.mfo.todoapp.ui.home.adapter

interface TaskItemClickListener {
    fun onDeleteTodoClicked(authorization: String, todoId: Long)
    fun onCompleteTodoClicked(authorization: String, todoId: Long)
    fun onDeleteCompletedTodos(authorization: String)
}