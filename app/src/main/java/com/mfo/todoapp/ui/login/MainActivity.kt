package com.mfo.todoapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfo.todoapp.data.network.TodoApiService
import com.mfo.todoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.btnLogin.setOnClickListener() {

        }
    }

    private fun getAll(authorization: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(TodoApiService::class.java).getAll(authorization)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.65.1:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}