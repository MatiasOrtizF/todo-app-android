package com.mfo.todoapp.ui.home.modal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfo.todoapp.R
import com.mfo.todoapp.domain.model.User

class UserAdapter(private var users: MutableList<User>): RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.user_list, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item: User = users[position]
        holder.bind(item)
    }
}