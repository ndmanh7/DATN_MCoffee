package com.example.mcoffee.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.ItemProductListBinding
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener

class ProductListAdapter(
    val productList : ArrayList<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductListHolder>() {

    private lateinit var itemClickListener: IOnProductItemClickListener
    inner class ProductListHolder(val binding: ItemProductListBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            itemClickListener.onProductItemClick(view!!, bindingAdapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductListBinding.inflate(inflater, parent, false)
        return ProductListHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        with(holder.binding) {
            tvProductName.text = productList[position].productName
            tvProductPrice.text = productList[position].price.toString() + " đ"

            Glide.with(this.root)
                .load(productList[position].image)
                .into(imgProduct)
        }


    }

    fun setClickListener(clickListener: IOnProductItemClickListener) {
        this.itemClickListener = clickListener
    }

}