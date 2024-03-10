package com.mfo.todoapp.ui.home.modal

import com.mfo.todoapp.domain.model.User

sealed class ModalState {
    data object Loading: ModalState()
    data class Error(val error: String): ModalState()
    data class Success(val users: MutableList<User>): ModalState()
}