package com.example.mcoffee.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.domain.repo.CartRepository
import com.example.mcoffee.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _recordList = MutableLiveData<ArrayList<Record>>()
    val recordList: LiveData<ArrayList<Record>> get() = _recordList

    private val _removeState = MutableLiveData<FireBaseState<String>>()
    val removeState: LiveData<FireBaseState<String>> get() = _removeState

    fun getProductsInCart() {
        viewModelScope.launch {
            cartRepository.getProductsInCart().collect {
                _recordList.postValue(it)
            }
        }
    }

    fun removeFromCart(recordList: List<Record>) {
        viewModelScope.launch(Dispatchers.IO) {
            _removeState.postValue(cartRepository.removeFromCart(recordList))
        }
    }

    fun order() {
        var order = Order(

        )
    }



}