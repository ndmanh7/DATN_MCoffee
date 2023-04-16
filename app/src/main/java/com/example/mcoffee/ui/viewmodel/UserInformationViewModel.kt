package com.example.mcoffee.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.user.Users
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInformationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userInfo = MutableLiveData<Users>()
    val userInfo: LiveData<Users> get() = _userInfo

    private val _updateProfileState = MutableSharedFlow<FireBaseState<String>>()
    val updateProfileState: SharedFlow<FireBaseState<String>> get() = _updateProfileState

    fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo().collect {
                Log.d("manh", "getUserInfo at line 29: $it")
                _userInfo.postValue(it)
            }
        }
    }

    fun updateUserProfile(user: Users?, updatedInformation: HashMap<String, Any?>) {
        viewModelScope.launch {
            _updateProfileState.emit(
                userRepository.updateUserProfile(user, updatedInformation)
            )
        }
    }

}