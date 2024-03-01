package com.mfo.todoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.domain.usecase.CompleteTodoUseCase
import com.mfo.todoapp.domain.usecase.DeleteCompletedTodosUseCase
import com.mfo.todoapp.domain.usecase.DeleteTodoUseCase
import com.mfo.todoapp.domain.usecase.GetAllTodoUseCase
import com.mfo.todoapp.domain.usecase.PostTodoUseCase
import com.mfo.todoapp.ui.login.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val getAllTodoUseCase: GetAllTodoUseCase, private val postTodoUseCase: PostTodoUseCase, private val deleteTodoUseCase: DeleteTodoUseCase, private val completeTodoUseCase: CompleteTodoUseCase, private val deleteCompletedTodosUseCase: DeleteCompletedTodosUseCase): ViewModel() {

    private var _state = MutableStateFlow<TaskState>(TaskState.Loading)
    val state: StateFlow<TaskState> = _state

    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    fun getAll(authorization: String) {
        viewModelScope.launch {
            _state.value = TaskState.Loading
            try {
                val result = withContext(Dispatchers.IO) { getAllTodoUseCase(authorization) }
                if(result != null) {
                    _todos.value = result
                    _state.value = TaskState.Success(result.toMutableList())
                } else {
                    _state.value = TaskState.Error("ocurrio un error, por favor intente mas tarde")
                }
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = TaskState.Error(errorMessage)
            }
        }
    }

    fun addTodo(authorization: String, task: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { postTodoUseCase(authorization, task) }
                getAll(authorization)
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = TaskState.Error(errorMessage)
            }
        }
    }

    fun deleteTodo(authorization: String, todoId: Long) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { deleteTodoUseCase(authorization, todoId) }
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = TaskState.Error(errorMessage)
            }
        }
    }

    fun completeTodo(authorization: String, todoId: Long) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { completeTodoUseCase(authorization, todoId) }
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = TaskState.Error(errorMessage)
            }
        }
    }

    fun deleteCompletedTodos(authorization: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { deleteCompletedTodosUseCase(authorization) }
                if(result) {
                    getAll(authorization)
                }
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = TaskState.Error(errorMessage)
            }
        }
    }
}