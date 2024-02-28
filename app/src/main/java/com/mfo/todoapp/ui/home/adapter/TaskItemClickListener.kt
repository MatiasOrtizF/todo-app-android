package com.mfo.todoapp.ui.home.adapter

import android.content.Intent
import com.mfo.todoapp.ui.login.MainActivity
import com.mfo.todoapp.utils.UserData

interface TaskItemClickListener {
    fun onDeleteTodoClicked(authorization: String, todoId: Long)
    fun onCompleteTodoClicked(authorization: String, todoId: Long)
}