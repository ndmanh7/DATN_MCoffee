package com.example.mcoffee.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.ItemCartBinding
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener

class CartAdapter() : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private lateinit var itemClickListener: IOnProductItemClickListener
    private lateinit var recordList: ArrayList<Record>


    var selectedItemIndex = MutableLiveData<MutableList<Int>>()
    var selectedItem = MutableLiveData<MutableList<Record>>()

    fun submitList(list: ArrayList<Record>) {
        this.recordList = list
    }

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

    override fun getItemCount(): Int = recordList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.apply {
            tvProductName.text = recordList[position].product?.productName
            tvAmountInCart.text = recordList[position].amount.toString()
            tvPrice.text = recordList[position].product?.price.toString()

            Glide.with(this.root)
                .load(recordList[position].product?.image)
                .into(imgProductImageCart)

            var amount = 1
            val totalPrice = recordList[position].product?.price!!

            btnAddInCart.setOnClickListener {
                tvAmountInCart.text = "${++amount}"
                tvPrice.text = "${amount * recordList[position].product?.price!!}"
                recordList[position].apply {
                    this.totalPrice = amount * recordList[position].product?.price!!
                    this.amount = amount
                }
            }

            btnMinusInCart.setOnClickListener {
                if (amount > 1) {
                    tvAmountInCart.text = "${--amount}"
                    tvPrice.text = "${amount * recordList[position].product?.price!!}"
                    recordList[position].apply {
                        this.totalPrice = amount * recordList[position].product?.price!!
                        this.amount = amount
                    }
                }
            }
            selectedItemIndex.value = arrayListOf()
            selectedItem.value = arrayListOf()
            recordList[position].apply {
                this.totalPrice = totalPrice
                this.amount = amount
            }


            cbItem.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItem.postValue(selectedItem.value?.apply { add(recordList[position]) } )
                } else {
                    selectedItem.postValue(selectedItem.value?.apply { remove(recordList[position]) } )

                }

            }

        }
    }

    fun setClickListener(clickListener: IOnProductItemClickListener) {
        this.itemClickListener = clickListener
    }

}