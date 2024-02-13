package com.mfo.todoapp.ui.home

import androidx.lifecycle.ViewModel
import com.mfo.todoapp.data.providers.TaskProvider
import com.mfo.todoapp.databinding.ActivityMainBinding
import com.mfo.todoapp.databinding.ActivityTaskBinding
import com.mfo.todoapp.domain.model.Todo
import com.mfo.todoapp.ui.login.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(taskProvider: TaskProvider): ViewModel() {

    private var _state = MutableStateFlow<TaskState>(TaskState.Loading)
    val state: StateFlow<TaskState> = _state

    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        _todos.value = taskProvider.getAll()
    }

    /*private fun getAll(authorization: String) {
        CoroutineScope(Dispatchers.IO).launch {
        }
    }*/
}