package com.example.mcoffee.ui.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.FragmentOrderBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.amount.observe(viewLifecycleOwner) { amount ->
            binding.apply {

                tvAmount.text = amount.toString()
                tvProductAmount.text = amount.toString()

                tvTotalPrice.text = (tvProductPrice.text.toString().toInt() * amount).toString()
                tvTotalPayment.text = "Tổng thanh toán: ${tvTotalPrice.text}"
            }
        }
    }

    override fun bindView() {
        super.bindView()

        //fetch data to views
        fetchProductInfo()


        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnOrder.setOnClickListener {
                addOrder()
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                viewModel.increaseAmount()
            }

            btnMinus.setOnClickListener {
                viewModel.decreaseAmount()
            }
        }
    }

    private fun fetchProductInfo() {
        val productInfo = arguments?.getSerializable("productInfo") as Product
        binding.apply {
            tvProductName.text = productInfo.productName

            Glide.with(root)
                .load(productInfo.image)
                .into(imgProductImage)

            tvProductPrice.text = productInfo.price.toString()
        }
    }

    private fun addOrder() {
        binding.apply {
            val order = Order()
            order.apply {
                productName = tvProductName.text.toString()
                orderDate = ""
                orderAmount = tvAmount.text.toString().toInt()
                price = tvProductPrice.text.toString().toInt()
                totalPrice = tvTotalPrice.text.toString().toInt()
            }
            viewModel.addToOrder(order)
        }
    }

}