package com.example.mcoffee.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.ItemOrderAdminBinding
import com.example.mcoffee.databinding.ItemOrderListBinding
import com.example.mcoffee.ui.base.BaseAdapter

class OrderHistoryAdapter : BaseAdapter<ItemOrderListBinding, Order>() {

    private lateinit var productListInOrderAdapter: ProductsInOrderAdapter
    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(binding: ItemOrderListBinding, item: Order) {
        binding.apply {
            productListInOrderAdapter = ProductsInOrderAdapter()
            item.also {
                productListInOrderAdapter.submitList(it.records)
                tvOrderDate.text = it.orderDate
                tvOrderTotalPrice.text = it.totalPrice.toString() + " đ"

                when(it.isConfirmed) {
                    "confirmed" -> {
                        tvOrderState.text = "Đã xác nhận"
                        tvOrderState.setTextColor(ContextCompat.getColor(recylerViewProductsInOrder.context, R.color.green))
                    }
                    "processing" -> {
                        tvOrderState.text = "Đang chờ xác nhận"
                        tvOrderState.setTextColor(ContextCompat.getColor(recylerViewProductsInOrder.context, R.color.green))

                    }
                    "aborted_by_user" -> {
                        tvOrderState.text = "Đã hủy bởi bạn"
                        tvOrderState.setTextColor(ContextCompat.getColor(recylerViewProductsInOrder.context, R.color.red))
                    }

                    "aborted_by_admin" -> {
                        tvOrderState.text = "Đã hủy bởi quản trị viên"
                        tvOrderState.setTextColor(ContextCompat.getColor(recylerViewProductsInOrder.context, R.color.red))
                    }
                }

                recylerViewProductsInOrder.apply {
                    adapter = productListInOrderAdapter
                    this.layoutManager = LinearLayoutManager(recylerViewProductsInOrder.context)
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemOrderListBinding
        get() = { inflater, parent, _ ->
            ItemOrderListBinding.inflate(inflater, parent, false)
        }

}