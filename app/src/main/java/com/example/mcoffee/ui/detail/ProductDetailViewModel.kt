package com.example.mcoffee.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.data.repo.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private var _orderAmount = MutableLiveData(1)
    var orderAmount: LiveData<Int> = _orderAmount

    private var _addToCartState = MutableLiveData<FireBaseState<String>>()
    val addToCartState: LiveData<FireBaseState<String>> get() = _addToCartState

    fun increaseOrderAmount() {
        _orderAmount.value = _orderAmount.value!! + 1
    }

    fun decreaseOrderAmount() {
        _orderAmount.value = _orderAmount.value!! - 1
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
                _addToCartState.value = cartRepository.addToCart(cart)
        }
    }

}