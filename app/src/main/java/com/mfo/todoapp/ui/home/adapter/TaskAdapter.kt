package com.mfo.todoapp.ui.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mfo.todoapp.R
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.utils.UserData

class TaskAdapter(private var todos: MutableList<Todo>, private val itemClickListener: TaskItemClickListener): RecyclerView.Adapter<TaskViewHolder>() {

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
        holder.itemView.findViewById<View>(R.id.btnShared).setOnClickListener{
            val dialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.modal, null)
            dialog.setContentView(view)
            dialog.show()
        }

        holder.itemView.findViewById<View>(R.id.btnDelete).setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete todo")
            builder.setMessage("Are you sure you want to deleted this todo?")

            builder.setPositiveButton("Yes") { dialog, which ->
                val token = UserData.token
                val todoId = todos[position].id
                itemClickListener.onDeleteTodoClicked(token, todoId)

                updateDeleteData(position)
            }

            builder.setNegativeButton("Cancel", null)

            val dialog = builder.create()
            dialog.show()
        }

        holder.itemView.findViewById<View>(R.id.btnCheck).setOnClickListener{
            val token = UserData.token
            val todoId = todos[position].id
            itemClickListener.onCompleteTodoClicked(token, todoId)

            updateCompleteData(position)
        }
    }

    fun updateData(newTodos: List<Todo>) {
        val startPosition = todos.size
        todos.clear()
        todos.addAll(newTodos)
        notifyItemInserted(startPosition)
    }

    private fun updateDeleteData(position: Int) {
        todos.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun updateCompleteData(position: Int) {
        todos[position].completed = !todos[position].completed
        notifyItemChanged(position)
    }
}