package com.example.mcoffee.ui.viewmodel.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminConfirmOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private var _confirmState = MutableLiveData<FireBaseState<String>>()
    val confirmState : LiveData<FireBaseState<String>> get() = _confirmState

    private var _abortState = MutableLiveData<FireBaseState<String>>()
    val abortState : LiveData<FireBaseState<String>> get() = _abortState

    private var _abortByUserState = MutableLiveData<FireBaseState<String>>()
    val abortByUserState : LiveData<FireBaseState<String>> get() = _abortByUserState

    fun confirmOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            _confirmState.postValue(orderRepository.confirmOrderByAdmin(order))
        }
    }

    fun abortOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            _abortState.postValue(orderRepository.abortOrderByAdmin(order))
        }
    }

    fun abortOrderByUser(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            _abortState.postValue(orderRepository.abortOrderByUser(order))
        }
    }

}