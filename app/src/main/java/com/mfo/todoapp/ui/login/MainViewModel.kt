package com.mfo.todoapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.todoapp.domain.model.LoginRequest
import com.mfo.todoapp.domain.usecase.GetLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getLoginUseCase: GetLoginUseCase): ViewModel() {

    private var _state = MutableStateFlow<MainState>(MainState.Loading)
    val state: StateFlow<MainState> = _state

    fun authenticationUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            val result = withContext(Dispatchers.IO) { getLoginUseCase(loginRequest) }
            if(result != null) {
                _state.value = MainState.Success(result.token)
            } else {
                _state.value = MainState.Error("ocurrio un error, por favor intente mas tarde")
            }
        }
    }
}