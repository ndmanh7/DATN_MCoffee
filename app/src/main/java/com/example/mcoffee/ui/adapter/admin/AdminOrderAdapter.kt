package com.example.mcoffee.ui.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.mcoffee.R
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
                when(it.isConfirmed) {
                    "confirmed" -> {
                        tvOrderState.text = "Đã xác nhận"
                        tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.green))
                    }
                    "processing" -> {
                        tvOrderState.text = "Đang chờ xác nhận"
                        tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.green))

                    }
                    "aborted_by_user" -> {
                        tvOrderState.text = "Đã hủy bởi khách hàng"
                        tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.red))
                    }

                    "aborted_by_admin" -> {
                        tvOrderState.text = "Đã hủy bởi quản trị viên"
                        tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.red))
                    }
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemOrderAdminBinding
        get() = { inflater, parent, _ ->
            ItemOrderAdminBinding.inflate(inflater, parent, false)
        }
}