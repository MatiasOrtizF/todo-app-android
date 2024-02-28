package com.mfo.todoapp.ui.home.adapter

import android.graphics.Paint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mfo.todoapp.databinding.ActivityTaskBinding
import com.mfo.todoapp.databinding.TodoListBinding
import com.mfo.todoapp.domain.model.Todo

class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = TodoListBinding.bind(view)

    fun bind(todo: Todo) {
        Log.d("TaskAdapter", "bind called for todo: $todo")
        binding.taskText.text = todo.task
        binding.taskText.paintFlags = if (todo.completed) {
            binding.taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.taskText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}