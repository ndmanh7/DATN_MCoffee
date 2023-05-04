package com.example.mcoffee.ui.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.ItemCategoryAdminBinding
import com.example.mcoffee.ui.base.BaseAdapter

class AdminCategoryAdapter : BaseAdapter<ItemCategoryAdminBinding, Category>() {
    override fun bindViewHolder(binding: ItemCategoryAdminBinding, item: Category) {
        binding.apply {
            item.also {
                tvItemCategoryName.text = it.categoryName
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemCategoryAdminBinding
        get() = { inflater, parent, _ ->
            ItemCategoryAdminBinding.inflate(inflater, parent, false)
        }
}