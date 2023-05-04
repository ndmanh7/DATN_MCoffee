package com.example.mcoffee.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.domain.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private var _oderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>> get() = _oderList

    private var _oderListByDate = MutableLiveData<List<Order>>()
    val oderListByDate: LiveData<List<Order>> get() = _oderListByDate

    fun getOrderList() {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getAllOrders().collect {
                _oderList.postValue(it)
            }
        }
    }

    fun getOrderListByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getAllOrdersByAdmin().collect { orderList ->
                val resultList = arrayListOf<Order>()
                for (order in orderList) {
                    if (order.orderDate.contains(date)) {
                        resultList.add(order)
                    }
                }
                Log.d("manh", "getOrderListByDate at line 42: $resultList")
                _oderListByDate.postValue(resultList)
            }
        }
    }

}