package com.example.mcoffee.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.ItemProductListBinding

class ProductListAdapter(
    val productList : ArrayList<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductListHolder>() {

    inner class ProductListHolder(val binding: ItemProductListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductListBinding.inflate(inflater, parent, false)
        return ProductListHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        with(holder.binding) {
            tvProductName.text = productList[position].productName
            tvProductPrice.text = productList[position].price.toString()
        }
    }

}