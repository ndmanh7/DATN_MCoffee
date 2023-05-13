package com.example.mcoffee.ui.viewmodel.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Record
import dagger.hilt.android.lifecycle.HiltViewModel

class AdminTopSaleViewModel: ViewModel() {

    fun getTopSale(orderList: List<Order>): List<Record> {
        var resultList = mutableListOf<Record>()
        var productIDList = mutableSetOf<String>()
        for (order in orderList) {
            for (record in order.records) {
                productIDList.add(record.product!!.uid)
            }
        }

        for (productId in productIDList) {
            var resultRecord = Record()
            for (order in orderList) {
                for (record in order.records) {
                    if (record.product!!.uid == productId) {
                        resultRecord.amount += record.amount
                        resultRecord.product = record.product
                    }
                }
            }
            resultList.add(resultRecord)
        }
        resultList.sortByDescending { it.amount }
        return resultList
    }

}