package com.example.mcoffee.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.ItemCategoryListBinding
import com.example.mcoffee.ui.base.BaseAdapter
import com.example.mcoffee.ui.interfaces.IOnCategoryItemClickListener

class CategoryAdapter(
    val categoryList: ArrayList<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryListHolder>(){

    private lateinit var itemClickListener: IOnCategoryItemClickListener

    inner class CategoryListHolder(val binding: ItemCategoryListBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener.onCategoryItemClick(view!!, bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryListBinding.inflate(inflater, parent, false)
        return CategoryListHolder(binding)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryListHolder, position: Int) {
        with(holder.binding) {
            tvCategoryName.text = categoryList[position].categoryName
        }
    }

    fun setClickListener(clickListener: IOnCategoryItemClickListener) {
        this.itemClickListener = clickListener
    }

}

//class CategoryAdapter : BaseAdapter<ItemCategoryListBinding, Category>() {
//    override fun bindViewHolder(binding: ItemCategoryListBinding, item: Category) {
//        binding.apply {
//            item.also {
//                tvCategoryName.text = it.categoryName
//            }
//        }
//    }
//
//    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemCategoryListBinding
//        get() = { inflater, parent, _ ->
//            ItemCategoryListBinding.inflate(inflater, parent, false)
//        }
//
//}