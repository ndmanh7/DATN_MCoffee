package com.example.mcoffee.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.window.Popup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Cart
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.databinding.FragmentProductDetailBinding
import com.example.mcoffee.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class ProductDetailFragment: BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        productDetailViewModel.orderAmount.observe(viewLifecycleOwner) { orderAmount ->
//            binding.tvAmount.text = orderAmount.toString()
        }

        productDetailViewModel.addToCartState.observe(viewLifecycleOwner) { resultState ->
            when(resultState) {
                is FireBaseState.Success -> {
                    findNavController().popBackStack()
                }

                else -> {
                    Toast.makeText(requireContext(), "Add failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun bindView() {
        super.bindView()
        showProductInfoFromHomeFragment()

        binding.apply {
//            btnAdd.setOnClickListener {
//                productDetailViewModel.increaseOrderAmount()
//            }
//
//            btnRemove.setOnClickListener {
//                productDetailViewModel.decreaseOrderAmount()
//            }
//
            btnAddToCart.setOnClickListener {
//                val cartInfo = Cart()
//                cartInfo.apply {
//                    productName = tvDetailProductName.text.toString()
//                    orderDate = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time)
////                    orderAmount = tvAmount.text.toString().toInt()
//                    price = tvProductPrice.text.toString().toInt()
//                    totalPrice = tvProductPrice.text.toString().toInt()
//                }
//                productDetailViewModel.addToCart(cartInfo)
                val productInfo : Product = requireActivity().intent.getSerializableExtra("productInfo") as Product
                findNavController().navigate(R.id.action_productDetailFragment_to_orderFragment, bundleOf("productInfo" to productInfo))
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }



    private fun showProductInfoFromHomeFragment() {
//        val productInfo : Product = arguments?.getSerializable("productInfo") as Product
        val productInfo : Product = requireActivity().intent.getSerializableExtra("productInfo") as Product

        binding.apply {
            productInfo.apply {
                tvDetailProductName.text = productName
                tvDetailProductDescription.text = description
                tvProductPrice.text = price.toString()

                Glide.with(root)
                    .load(image)
                    .into(imgDetailProduct)
            }
        }
    }

}