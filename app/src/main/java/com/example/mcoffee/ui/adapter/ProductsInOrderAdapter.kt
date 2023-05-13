package com.example.mcoffee.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.ItemProductsInOrderBinding

class ProductsInOrderAdapter : RecyclerView.Adapter<ProductsInOrderAdapter.ProductsInOrderViewHolder>() {
    private lateinit var recordList: List<Record>

    fun submitList(recordList: List<Record>) {
        this.recordList = recordList
    }

    inner class ProductsInOrderViewHolder(val binding: ItemProductsInOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsInOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductsInOrderBinding.inflate(inflater, parent, false)
        return ProductsInOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = recordList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsInOrderViewHolder, position: Int) {
        holder.binding.apply {
            recordList[position].also {
                tvProductInfo.text = "${it.product?.productName} (${it.product?.price}) x ${it.amount}"
                tvTotalPrice.text = it.totalPrice.toString() + " đ"
            }
        }
    }
}