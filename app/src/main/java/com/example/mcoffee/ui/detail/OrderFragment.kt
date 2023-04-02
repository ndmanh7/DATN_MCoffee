package com.example.mcoffee.ui.detail

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.FragmentOrderBinding
import com.example.mcoffee.ui.base.BaseFragment

class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    override fun bindView() {
        super.bindView()
        val productInfo = arguments?.getSerializable("productInfo") as Product

        //fetch data to views
        binding.apply {
            tvProductName.text = productInfo.productName

            Glide.with(root)
                .load(productInfo.image)
                .into(imgProductImage)

            tvProductPrice.text = productInfo.price.toString()

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}