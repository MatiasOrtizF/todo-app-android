package com.mfo.todoapp.ui.home.adapter

interface TaskAdapterListener {
    fun onDeleteItem(position: Int)
    fun onCompleteItem(position: Int)
}