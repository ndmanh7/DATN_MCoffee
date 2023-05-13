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
import kotlinx.coroutines.flow.collect
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

    private var _revenueByMonth = MutableLiveData<Int>()
    val revenueByMonth: LiveData<Int> get() = _revenueByMonth

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
                _oderListByDate.postValue(resultList)
            }
        }
    }

    fun getRevenueByMonth(orderListByYear: List<Order>, month: String): Int {
        var totalMonthRevenue = 0
        for (order in orderListByYear) {
            if (order.isConfirmed == "confirmed") {
                val orderMonth = order.orderDate.split("-")[1]
                if (orderMonth.contains(month)) {
                    totalMonthRevenue += order.totalPrice
                }
            }
        }
        return totalMonthRevenue

    }

}