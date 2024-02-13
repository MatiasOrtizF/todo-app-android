package com.mfo.todoapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.mfo.todoapp.data.network.TodoApiService
import com.mfo.todoapp.databinding.ActivityMainBinding
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.ui.home.TaskActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        initUIState()
        binding.btnLogin.setOnClickListener() {
            val loginRequest = LoginRequest(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            /*val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)*/
            mainViewModel.authenticationUser(loginRequest)
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.collect{
                    when(it) {
                        is MainState.Error -> errorState()
                        MainState.Loading -> loadingState()
                        is MainState.Success -> successSate(it)
                    }
                }
            }
        }
    }

    private fun authenticationUser(loginRequest: LoginRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(TodoApiService::class.java).authenticationUser(loginRequest)
            println(myResponse)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.65.1:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun loadingState() {
        binding.pb.isVisible = true
    }

    private fun errorState() {
        binding.pb.isVisible = false
    }

    private fun successSate(state: MainState.Success) {
        binding.pb.isVisible = false
        binding.tokenTxt.text = state.token
    }
}