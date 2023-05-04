package com.example.mcoffee.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.ItemSearchResultBinding
import com.example.mcoffee.ui.base.BaseAdapter


class SearchAdapter : BaseAdapter<ItemSearchResultBinding, Product>() {
    override fun bindViewHolder(binding: ItemSearchResultBinding, item: Product) {
        binding.apply {
            item.also {
                tvProductName.text = it.productName
                tvProductDescription.text = it.description
                Glide.with(root)
                    .load(it.image)
                    .into(imgProductImage)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemSearchResultBinding
        get() = { inflater, parent, _ ->
            ItemSearchResultBinding.inflate(inflater, parent, false)
        }
}