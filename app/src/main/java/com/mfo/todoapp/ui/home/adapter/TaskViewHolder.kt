package com.mfo.todoapp.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mfo.todoapp.databinding.ActivityTaskBinding
import com.mfo.todoapp.databinding.TodoListBinding
import com.mfo.todoapp.domain.model.Todo

class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = TodoListBinding.bind(view)

    fun bind(todo: Todo) {
        binding.taskText.text = todo.task
    }
}