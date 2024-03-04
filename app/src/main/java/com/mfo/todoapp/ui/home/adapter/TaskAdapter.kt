package com.mfo.todoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfo.todoapp.R
import com.mfo.todoapp.domain.model.Todo

class TaskAdapter(private var todos: MutableList<Todo>, private val listener: TaskItemClickListener): RecyclerView.Adapter<TaskViewHolder>(), TaskAdapterListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.todo_list, parent, false), listener, this)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item: Todo = todos[position]
        holder.bind(item)
    }

    fun updateData(newTodos: List<Todo>) {
        val startPosition = todos.size
        todos.clear()
        todos.addAll(newTodos)
        notifyItemInserted(startPosition)
    }

    override fun onAddItem(todo: Todo) {
        todos.add(index = todos.size, todo)
        notifyItemInserted(todos.size-1)
    }

    override fun onDeleteItem(position: Int) {
        todos.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCompleteItem(position: Int) {
        todos[position].completed = !todos[position].completed
        notifyItemChanged(position)
    }
}