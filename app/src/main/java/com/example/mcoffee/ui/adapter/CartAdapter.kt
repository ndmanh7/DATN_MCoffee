package com.example.mcoffee.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.ItemCartBinding
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener

class CartAdapter(
    val productList: ArrayList<Product>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private lateinit var itemClickListener: IOnProductItemClickListener

    var selectedItemIndex = MutableLiveData<MutableList<Int>>()
    var selectedItem = MutableLiveData<MutableList<Order>>()

    inner class CartViewHolder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener.onProductItemClick(view!!, bindingAdapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.apply {
            tvProductName.text = productList[position].productName
            tvAmountInCart.text = "1"

            Glide.with(this.root)
                .load(productList[position].image)
                .into(imgProductImageCart)

            var amount = tvAmountInCart.text.toString().toInt()

            btnAddInCart.setOnClickListener {
                tvAmountInCart.text = "${++amount}"
                tvPrice.text = "${amount*35000}"
            }

            btnMinusInCart.setOnClickListener {
                if (amount > 1) {
                    tvAmountInCart.text = "${--amount}"
                    tvPrice.text = "${amount*35000}"
                }
            }
            selectedItemIndex.value = arrayListOf()

            cbItem.setOnCheckedChangeListener { _, isChecked ->
                val order = Order(
                    productName = tvProductName.text.toString(),
                    orderAmount = tvAmountInCart.text.toString().toInt(),
                )
                if (isChecked) {
                    selectedItemIndex.postValue(selectedItemIndex.value?.apply { add(position) } )
                } else {
                    selectedItemIndex.postValue(selectedItemIndex.value?.apply { remove(position) } )

                }

            }

        }
    }

    fun setClickListener(clickListener: IOnProductItemClickListener) {
        this.itemClickListener = clickListener
    }

}