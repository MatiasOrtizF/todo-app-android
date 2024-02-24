package com.mfo.todoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.usecase.GetAllTodoUseCase
import com.mfo.todoapp.domain.usecase.PostTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val getAllTodoUseCase: GetAllTodoUseCase, private val postTodoUseCase: PostTodoUseCase): ViewModel() {

    private var _state = MutableStateFlow<TaskState>(TaskState.Loading)
    val state: StateFlow<TaskState> = _state

    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    fun getAll(authorization: String) {
        viewModelScope.launch {
            _state.value = TaskState.Loading
            val result = withContext(Dispatchers.IO) { getAllTodoUseCase(authorization) }
            if(result != null) {
                _todos.value = result
                _state.value = TaskState.Success(result.toMutableList())
            } else {
                _state.value = TaskState.Error("ocurrio un error, por favor intente mas tarde")
            }
        }
    }

    fun addTodo(authorization: String, task: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { postTodoUseCase(authorization, task) }
            getAll(authorization)
        }
    }
}