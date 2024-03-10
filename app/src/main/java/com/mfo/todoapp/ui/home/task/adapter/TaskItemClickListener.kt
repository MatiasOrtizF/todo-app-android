package com.mfo.todoapp.ui.home.task.adapter

interface TaskItemClickListener {
    fun onDeleteTodoClicked(authorization: String, todoId: Long)
    fun onCompleteTodoClicked(authorization: String, todoId: Long)
    fun onDeleteCompletedTodos(authorization: String)
    fun getAllUsersInTodoShared(authorization: String, todoId: Long)
}