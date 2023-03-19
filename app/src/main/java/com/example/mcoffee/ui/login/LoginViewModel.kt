package com.example.mcoffee.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.remote.AuthRequestState
import com.example.mcoffee.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _loginState = MutableSharedFlow<AuthRequestState>()
    val loginState: SharedFlow<AuthRequestState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.emit(userRepository.login(email, password))
        }
    }
}