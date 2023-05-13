package com.example.mcoffee.ui.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.ItemTopSaleProductBinding
import com.example.mcoffee.ui.base.BaseAdapter

class AdminTopSaleAdapter : BaseAdapter<ItemTopSaleProductBinding, Record>() {
    override fun bindViewHolder(binding: ItemTopSaleProductBinding, item: Record) {
        binding.apply {
            tvProductName.text = item.product?.productName
            tvProductSaleAmount.text = item.amount.toString()
            Glide.with(imgProductImageCart.context)
                .load(item.product?.image)
                .into(imgProductImageCart)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemTopSaleProductBinding
        get() = { inflater, parent, _ ->
            ItemTopSaleProductBinding.inflate(inflater, parent, false)
        }

}