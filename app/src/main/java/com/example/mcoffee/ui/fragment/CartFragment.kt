package com.example.mcoffee.ui.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.databinding.FragmentCartBinding
import com.example.mcoffee.ui.adapter.CartAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener
import com.example.mcoffee.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate),
    IOnProductItemClickListener {

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var mCartAdapter: CartAdapter
    override fun observeViewModel() {
        super.observeViewModel()
        showProductsInCart()
    }

    override fun bindView() {
        super.bindView()
        cartViewModel.getProductsInCart()

        binding.apply {
            btnCartBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnOrder.setOnClickListener {
                order()
//                findNavController().popBackStack()
            }
        }
    }

    private fun showProductsInCart() {
        cartViewModel.productList.observe(viewLifecycleOwner) {
            mCartAdapter = CartAdapter(it)
            binding.recyclerViewProductsInCart.apply {
                adapter = mCartAdapter
                layoutManager = LinearLayoutManager(requireContext())
                mCartAdapter.setClickListener(this@CartFragment)
            }
        }
    }

    private fun order() {
        mCartAdapter.selectedItemIndex.observe(viewLifecycleOwner) {
            for (index in it) {
                cartViewModel.order(index)
            }
        }
    }

    override fun onProductItemClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }

}