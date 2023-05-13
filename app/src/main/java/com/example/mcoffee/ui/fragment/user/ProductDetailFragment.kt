package com.example.mcoffee.ui.fragment.user

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.databinding.FragmentProductDetailBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment: BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun observeViewModel() {
        super.observeViewModel()
        productDetailViewModel.orderAmount.observe(viewLifecycleOwner) { orderAmount ->
//            binding.tvAmount.text = orderAmount.toString()
        }

        productDetailViewModel.addToCartState.observe(viewLifecycleOwner) { resultState ->
            when(resultState) {
                is FireBaseState.Success -> {
//                    findNavController().popBackStack()
                }

                else -> {
                    Toast.makeText(requireContext(), "Add failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun bindView() {
        super.bindView()
        showProductInfoFromHomeFragment()
        val productInfo = args.productDetail
        var amountOrder = 1

        binding.apply {
            btnOrder.text = productInfo.price.toString() +" đ"
            btnOrder.setOnClickListener {
                val record = Record(
                    product = productInfo,
                    amount = amountOrder,
                    totalPrice = productInfo.price * amountOrder
                )
                findNavController().navigate(R.id.action_productDetailFragment_to_orderFragment, bundleOf("record_from_detail_screen" to record))
            }

            imgAddToCart.setOnClickListener {
                addToCart()
            }

            //change product amount

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                ++amountOrder
                tvAmount.text = "$amountOrder"
                btnOrder.text = "${amountOrder * productInfo.price} đ"
            }

            btnMinus.setOnClickListener {
                if (amountOrder > 1) {
                    --amountOrder
                    tvAmount.text = "$amountOrder"
                    btnOrder.text = "${amountOrder * productInfo.price} đ"
                }
            }
        }
    }

    private fun addToCart() {
        val productInfo =  args.productDetail
        val record = Record(
            product = productInfo,
            amount = 1
        )
        productDetailViewModel.addToCart(record)
        Toast.makeText(requireContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
    }

    private fun showProductInfoFromHomeFragment() {
        val productInfo =  args.productDetail
        binding.apply {
            productInfo.apply {
                tvDetailProductName.text = productName
                tvDetailProductDescription.text = description

                Glide.with(root)
                    .load(image)
                    .into(imgDetailProduct)
            }
        }
    }

}