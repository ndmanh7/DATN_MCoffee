package com.example.mcoffee.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.ItemInOrderBinding

class ProductListInOrderAdapter : RecyclerView.Adapter<ProductListInOrderAdapter.ProductListInOrderViewHolder>() {

    lateinit var recordList: List<Record>

    fun submitList(list: List<Record>) {
        this.recordList = list
    }

    inner class ProductListInOrderViewHolder(val binding: ItemInOrderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListInOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInOrderBinding.inflate(inflater, parent, false)
        return ProductListInOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = recordList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductListInOrderViewHolder, position: Int) {
        holder.binding.apply {
            recordList[position].also {
                tvProductName.text = it.product?.productName
                tvAmountInCart.text = "x" + it.amount.toString()
                tvPrice.text = it.totalPrice.toString() + " đ"

                btnAddInCart.visibility = View.GONE
                btnMinusInCart.visibility = View.GONE

                Glide.with(root)
                    .load(it.product?.image)
                    .into(imgProductImageCart)
            }
        }
    }


}