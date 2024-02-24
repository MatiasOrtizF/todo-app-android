package com.mfo.todoapp.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mfo.todoapp.R
import com.mfo.todoapp.domain.model.Todo

class TaskAdapter(private var todos: MutableList<Todo>): RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.todo_list, parent, false))
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item: Todo = todos[position]
        holder.bind(item)
        val context = holder.itemView.context

       // Configuración del listener de clics en el botón compartido
        holder.itemView.findViewById<View>(R.id.sharedBtn).setOnClickListener {
            val dialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.modal, null)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    fun updateData(newTodos: List<Todo>) {
        val startPosition = todos.size
        todos.clear()
        todos.addAll(newTodos)
        notifyItemInserted(startPosition)
    }
}