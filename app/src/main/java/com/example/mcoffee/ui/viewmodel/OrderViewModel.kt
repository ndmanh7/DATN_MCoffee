package com.example.mcoffee.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {

    private var _addOrderState = MutableLiveData<FireBaseState<String>>()
    val addOrderState: LiveData<FireBaseState<String>> get() = _addOrderState

    private var _amount = MutableLiveData<Int>(1)
    val amount: LiveData<Int> get() = _amount

    fun addToOrder(order: Order) {
        viewModelScope.launch {
            _addOrderState.value = orderRepository.addOrder(order)
        }
    }

}