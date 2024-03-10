package com.mfo.todoapp.ui.home.modal.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mfo.todoapp.databinding.UserListBinding
import com.mfo.todoapp.domain.model.User

class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = UserListBinding.bind(view)

    fun bind(user: User) {
        binding.tvUserName.text = user.name
    }
}