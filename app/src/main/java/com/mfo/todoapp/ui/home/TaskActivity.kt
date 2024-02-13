package com.mfo.todoapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mfo.todoapp.R
import com.mfo.todoapp.databinding.ActivityMainBinding
import com.mfo.todoapp.databinding.ActivityTaskBinding
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.ui.login.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskViewModel.state.collect{
                    when(it) {
                        is TaskState.Error -> errorState()
                        TaskState.Loading -> loadingState()
                        is TaskState.Success -> successSate()
                    }
                }
            }
        }
    }

    private fun loadingState() {

    }

    private fun errorState() {

    }

    private fun successSate() {

    }
}