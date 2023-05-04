package com.example.mcoffee.ui.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.ItemOrderAdminBinding
import com.example.mcoffee.ui.base.BaseAdapter


class AdminOrderAdapter : BaseAdapter<ItemOrderAdminBinding, Order>() {
    override fun bindViewHolder(binding: ItemOrderAdminBinding, item: Order) {
        binding.apply {
            item.also {
                tvOrderId.text = it.uid
                tvUserName.text = it.receiverName
                tvOrderDate.text = it.orderDate
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemOrderAdminBinding
        get() = { inflater, parent, _ ->
            ItemOrderAdminBinding.inflate(inflater, parent, false)
        }
}