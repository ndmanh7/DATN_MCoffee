package com.example.mcoffee.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.remote.user.AuthRequestState
import com.example.mcoffee.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthRequestState>(AuthRequestState.Fail(""))
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.emit(userRepository.login(email, password))
        }
    }

    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _loginState.emit(AuthRequestState.Fail(""))
                userRepository.logout()
            }
        }
    }

    fun clearLoginState() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _loginState.emit(AuthRequestState.Fail(""))
            }
        }
    }
}