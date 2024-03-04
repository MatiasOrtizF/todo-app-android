package com.mfo.todoapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfo.todoapp.R
import com.mfo.todoapp.databinding.ActivityTaskBinding
import com.mfo.todoapp.databinding.TodoListBinding
import com.mfo.todoapp.ui.home.adapter.TaskAdapter
import com.mfo.todoapp.ui.home.adapter.TaskItemClickListener
import com.mfo.todoapp.ui.login.MainActivity
import com.mfo.todoapp.utils.UserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskActivity: AppCompatActivity(), TaskItemClickListener{

    private lateinit var binding: ActivityTaskBinding
    private lateinit var todoListBinding: TodoListBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private val taskAdapter = TaskAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        todoListBinding = TodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponent()
        initUIState()
        initUI()
    }

    private fun initComponent() {
        val token: String = UserData.token
        initListeners(token)
    }

    private fun initListeners(token: String) {
        binding.btnLogOut.setOnClickListener() {
            logOut()
        }
        binding.etTask.setOnEditorActionListener { _, actionId, _ ->
            if(binding.etTask.text.toString().isNotEmpty()) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    val task: String = binding.etTask.text.toString()
                    taskViewModel.addTodo(token, task)
                }
            }
            true
        }
        binding.btnClearCompleted.setOnClickListener {
            taskViewModel.deleteCompletedTodos(token)
        }
        binding.btnAllFilter.setOnClickListener {
            val filteredTodos = taskViewModel.getAllTodos()
            taskAdapter.updateTodos(filteredTodos)
        }
        binding.btnActivedFilter.setOnClickListener {
            val filteredTodos = taskViewModel.getTodosByStatus(completed = false)
            taskAdapter.updateTodos(filteredTodos)
        }
        binding.btnCompletedFilter.setOnClickListener {
            val filteredTodos = taskViewModel.getTodosByStatus(completed = true)
            taskAdapter.updateTodos(filteredTodos)
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val token: String = UserData.token
                taskViewModel.getAll(token)

                taskViewModel.state.collect{
                    when(it) {
                        is TaskState.Error -> {
                            errorState(it.error)
                        }
                        TaskState.Loading -> loadingState()
                        is TaskState.Success -> successSate(it)
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            adapter = taskAdapter
        }
    }

    private fun loadingState() {
        binding.pb.isVisible = true
    }

    private fun errorState(error: String) {
        binding.pb.isVisible = false
        val context = binding.root.context
        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
        if(error == "Unauthorized: invalid token") {
            logOut()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun successSate(state: TaskState.Success) {
        binding.pb.isVisible = false
        taskAdapter.updateData(state.todos)
        /*binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = TaskAdapter(state.todos)*/
        binding.recyclerView.adapter = taskAdapter
        addNumberInItemsLeft(state.todos.size);
    }

    private fun logOut() {
        UserData.token = ""
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun addNumberInItemsLeft(todosSize: Int) {
        val itemCount: String = todosSize.toString()
        val itemsLeftText = getString(R.string.txt_items_left, itemCount)
        binding.itemsLeftText.text = itemsLeftText
    }

    override fun onDeleteTodoClicked(authorization: String, todoId: Long) {
        taskViewModel.deleteTodo(authorization, todoId)
    }

    override fun onCompleteTodoClicked(authorization: String, todoId: Long) {
        taskViewModel.completeTodo(authorization, todoId)
    }

    override fun onDeleteCompletedTodos(authorization: String) {
        taskViewModel.deleteCompletedTodos(authorization)
    }
}