package com.mfo.todoapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.todoapp.data.providers.TaskProvider
import com.mfo.todoapp.databinding.ActivityMainBinding
import com.mfo.todoapp.databinding.ActivityTaskBinding
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.usecase.GetAllTodoUseCase
import com.mfo.todoapp.domain.usecase.GetLoginUseCase
import com.mfo.todoapp.ui.login.MainState
import com.mfo.todoapp.utils.Constants
import com.mfo.todoapp.utils.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val getAllTodoUseCase: GetAllTodoUseCase): ViewModel() {

    private var _state = MutableStateFlow<TaskState>(TaskState.Loading)
    val state: StateFlow<TaskState> = _state

    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        getAll(UserData.token)
    }
    fun getAll(authorization: String) {
        viewModelScope.launch {
            _state.value = TaskState.Loading
            val result = withContext(Dispatchers.IO) { getAllTodoUseCase(authorization) }
            if(result != null) {
                println(result)
                //_state.value = TaskState.Success(result)
            } else {
                _state.value = TaskState.Error("ocurrio un error, por favor intente mas tarde")
            }
        }
    }

    /*private fun getAll(authorization: String) {
        CoroutineScope(Dispatchers.IO).launch {
        }
    }*/
}