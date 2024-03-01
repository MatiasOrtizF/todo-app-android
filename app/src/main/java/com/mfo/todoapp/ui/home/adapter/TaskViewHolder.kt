package com.mfo.todoapp.ui.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mfo.todoapp.R
import com.mfo.todoapp.databinding.TodoListBinding
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.utils.UserData

class TaskViewHolder(view: View, private val listener: TaskItemClickListener, private val adapterListener: TaskAdapterListener): RecyclerView.ViewHolder(view) {
    private val binding = TodoListBinding.bind(view)


    fun bind(todo: Todo) {
        val token = UserData.token
        val todoId = todo.id

        binding.taskText.text = todo.task
        if (todo.completed) {
            binding.taskText.paintFlags = binding.taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.btnCheck.setImageResource(R.drawable.check)
        } else {
            binding.taskText.paintFlags = binding.taskText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.btnCheck.setImageResource(R.drawable.circle)
        }
        binding.btnShared.setOnClickListener {
            val context = binding.btnShared.context
            val dialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.modal, null)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(binding.btnDelete.context)
            builder.setTitle("Delete todo")
            builder.setMessage("Are you sure you want to deleted this todo?")

            builder.setPositiveButton("Yes") { dialog, which ->

                listener.onDeleteTodoClicked(token, todoId)

                adapterListener.onDeleteItem(adapterPosition)
            }

            builder.setNegativeButton("Cancel", null)

            val dialog = builder.create()
            dialog.show()
        }
        binding.btnCheck.setOnClickListener {
            listener.onCompleteTodoClicked(token, todoId)

            adapterListener.onCompleteItem(adapterPosition)
        }
    }


}