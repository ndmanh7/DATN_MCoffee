package com.example.mcoffee.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.domain.repo.CartRepository
import com.example.mcoffee.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _productList = MutableLiveData<ArrayList<Product>>()
    val productList: LiveData<ArrayList<Product>> get() = _productList

    fun getProductsInCart() {
        viewModelScope.launch {
            cartRepository.getProductsInCart().collect {
                _productList.postValue(it)
            }
        }
    }

     fun order(position: Int) {
        viewModelScope.launch {
            for (index in _productList.value?.indices!!) {
                Log.d("manh", "order at line 37: $position")
                if (position == index) {
                    val order = Order()
                    order.apply {
                        _productList.value!![index].also {
                            productName = it.productName
                            orderDate = ""
                            orderAmount = 0
                            price = it.price
                            totalPrice = it.price
                        }
                    }
                    orderRepository.addOrder(order)
                    cartRepository.removeFromCart(productList.value!![index])
                }
            }
        }
    }



}