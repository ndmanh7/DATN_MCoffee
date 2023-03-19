package com.example.mcoffee.ui.register

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.remote.AuthRequestState
import com.example.mcoffee.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerState = MutableSharedFlow<AuthRequestState>()
    val registerState: SharedFlow<AuthRequestState> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.emit(userRepository.register(email, password))
        }
    }


}