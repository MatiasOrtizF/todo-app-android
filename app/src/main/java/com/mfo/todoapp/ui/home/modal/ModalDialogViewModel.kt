package com.mfo.todoapp.ui.home.modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.todoapp.domain.model.User
import com.mfo.todoapp.domain.usecase.GetUsersInTodoSharedUseCase
import com.mfo.todoapp.domain.usecase.PostTodoSharedUseCase
import com.mfo.todoapp.ui.home.task.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ModalDialogViewModel @Inject constructor(private val getUsersInTodoSharedUseCase: GetUsersInTodoSharedUseCase, private val postTodoSharedUseCase: PostTodoSharedUseCase): ViewModel() {
    private var _state = MutableStateFlow<ModalState>(ModalState.Loading)
    val state: StateFlow<ModalState> = _state

    private var _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun getAllUsers(authorization: String, todoId: Long) {
        viewModelScope.launch {
            _state.value = ModalState.Loading
            try {
                val result = withContext(Dispatchers.IO) { getUsersInTodoSharedUseCase(authorization, todoId) }
                if(result != null) {
                    _users.value = result
                    _state.value = ModalState.Success(result.toMutableList())
                } else {
                    _state.value = ModalState.Error("ocurrio un error, por favor intente mas tarde")
                }
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = ModalState.Error(errorMessage)
            }
        }
    }

    fun addTodoShared(authorization: String, todoId: Long, userEmail: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { postTodoSharedUseCase(authorization, todoId, userEmail) }
                getAllUsers(authorization, todoId)
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = ModalState.Error(errorMessage)
            }
        }
    }


}