package com.mfo.todoapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mfo.todoapp.databinding.ActivityMainBinding
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.ui.home.task.TaskActivity
import com.mfo.todoapp.utils.PreferenceHelper
import com.mfo.todoapp.utils.PreferenceHelper.get
import com.mfo.todoapp.utils.PreferenceHelper.set
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

        val preferences = PreferenceHelper.defaultPrefs(this)
        if(preferences["jwt", ""].contains("."))
            goToTask()
    }

    private fun initUI() {
        initUIState()
        binding.btnLogin.setOnClickListener() {
            val loginRequest = LoginRequest(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            println(loginRequest)
            mainViewModel.authenticationUser(loginRequest)
            loadingState()
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.collect{
                    when(it) {
                        is MainState.Error -> errorState(it.error)
                        MainState.Loading -> {}
                        is MainState.Success -> successSate(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.pb.isVisible = true
    }

    private fun errorState(error: String) {
        binding.pb.isVisible = false
        val context = binding.root.context
        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    private fun successSate(state: MainState.Success) {
        binding.pb.isVisible = false
        //binding.tokenTxt.text = state.token
        val jwt: String = state.token
        createSessionPreference(jwt)
        goToTask()
        //saveToken(state.token)
    }

    /*private fun saveToken(token: String) {

    }*/
    private fun goToTask() {
        val intent = Intent(this, TaskActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createSessionPreference(jwt: String) {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["jwt"] = jwt
    }
}