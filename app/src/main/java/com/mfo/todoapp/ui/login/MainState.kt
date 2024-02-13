package com.mfo.todoapp.ui.login

sealed class MainState {
    data object Loading: MainState()
    data class Error(val error: String): MainState()
    data class Success(val token: String): MainState()
}