package com.example.mcoffee.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseAdapter<VB : ViewBinding, T : Any> :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {
    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    private var list: List<T> = listOf()
    private var onItemClickListener: ((item: T) -> Unit)? = null

    abstract fun bindViewHolder(binding: VB, item: T)

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> VB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, viewType)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val item = list[position]
        bindViewHolder(holder.binding, item)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                it(item)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<T>) {
        list = newList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (item: T) -> Unit) {
        onItemClickListener = listener
    }

}