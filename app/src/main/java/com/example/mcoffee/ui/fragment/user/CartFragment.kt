package com.example.mcoffee.ui.fragment.user

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.databinding.FragmentCartBinding
import com.example.mcoffee.ui.adapter.CartAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener
import com.example.mcoffee.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate),
    IOnProductItemClickListener {

    private val cartViewModel: CartViewModel by activityViewModels()

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
        cartViewModel.recordList.observe(viewLifecycleOwner) {
            mCartAdapter = CartAdapter()
            mCartAdapter.submitList(it)
            binding.recyclerViewProductsInCart.apply {
                adapter = mCartAdapter
                layoutManager = LinearLayoutManager(requireContext())
                mCartAdapter.setClickListener(this@CartFragment)
            }
        }
    }

    private fun order() {
        mCartAdapter.selectedItem.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_cartFragment_to_orderFragment,
                bundleOf("records" to it)
            )
        }
    }

    override fun onProductItemClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }

}