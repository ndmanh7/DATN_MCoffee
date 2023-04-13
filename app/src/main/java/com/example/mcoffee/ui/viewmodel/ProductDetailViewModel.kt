package com.example.mcoffee.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.domain.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val cartRepository: CartRepository,
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

    fun addToCart(product: Product) {
        viewModelScope.launch {
            _addToCartState.value = cartRepository.addToCart(product)
        }
    }


}