package com.mfo.todoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.usecase.GetAllTodoUseCase
import com.mfo.todoapp.ui.login.MainState
import com.mfo.todoapp.utils.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getAll(authorization: String) {
        viewModelScope.launch {
            _state.value = TaskState.Loading
            println("token es: $authorization")
            val result = withContext(Dispatchers.IO) { getAllTodoUseCase(authorization) }
            println("este es el resultado: $result")
            if(result != null) {
                _todos.value = result
                _state.value = TaskState.Success(result)
            } else {
                _state.value = TaskState.Error("ocurrio un error, por favor intente mas tarde")
            }
        }
    }


    /*init {
        getAll(UserData.token)
    }
    fun getAll(authorization: String) {
        viewModelScope.launch {
            _state.value = TaskState.Loading
            val result = withContext(Dispatchers.IO) { getAllTodoUseCase(authorization) }
            if(result != null) {
                val todosList = (result as? TaskState.Success)?.todos
                if (todosList != null) {
                    _todos.value = todosList
                } else {
                    // Manejar el caso si no se puede extraer la lista de todos
                    _state.value = TaskState.Error("Error al obtener la lista de todos")
                }
            } else {
                _state.value = TaskState.Error("ocurrio un error, por favor intente mas tarde")
            }
        }
    }*/
}