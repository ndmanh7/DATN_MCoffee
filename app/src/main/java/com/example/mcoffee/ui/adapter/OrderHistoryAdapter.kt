package com.example.mcoffee.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.ItemOrderListBinding

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    private lateinit var orderList: List<Order>
    private lateinit var productListInOrderAdapter: ProductsInOrderAdapter

    fun submitList(orderList: List<Order>) {
        this.orderList = orderList
    }


    inner class OrderHistoryViewHolder(val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderListBinding.inflate(inflater, parent, false)
        return OrderHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = orderList.size


    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.binding.apply {
            productListInOrderAdapter = ProductsInOrderAdapter()
            orderList[position].also {
                productListInOrderAdapter.submitList(it.records)
                tvOrderId.text = it.uid
                tvUserName.text = it.receiverName
                tvUserPhone.text = it.receiverPhone
                tvUserAddress.text = it.receiverAddress
                tvOrderDate.text = it.orderDate
                tvOrderTotalPrice.text = it.totalPrice.toString()

                recylerViewProductsInOrder.apply {
                    adapter = productListInOrderAdapter
                    this.layoutManager = LinearLayoutManager(holder.binding.recylerViewProductsInOrder.context)
                }
            }
        }
    }

}