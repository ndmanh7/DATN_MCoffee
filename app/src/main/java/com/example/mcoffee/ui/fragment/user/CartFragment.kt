package com.example.mcoffee.ui.fragment.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.remote.FireBaseState
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCartAdapter = CartAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        showProductsInCart()

        cartViewModel.removeState.observe(viewLifecycleOwner) {
            when (it) {
                is FireBaseState.Success -> Toast.makeText(
                    requireContext(),
                    "Xóa thành công",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
            }
        }
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
            mCartAdapter.selectedItem.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.btnDelete.visibility = View.VISIBLE
                } else {
                    binding.btnDelete.visibility = View.INVISIBLE
                }
            }

            btnDelete.setOnClickListener {
                removeFromCart()
            }
        }
    }

    private fun showProductsInCart() {
        cartViewModel.recordList.observe(viewLifecycleOwner) {
            mCartAdapter.submitList(it)
            binding.recyclerViewProductsInCart.apply {
                adapter = mCartAdapter
                layoutManager = LinearLayoutManager(requireContext())
                mCartAdapter.setClickListener(this@CartFragment)
            }
            binding.recyclerViewProductsInCart.setOnLongClickListener {
                return@setOnLongClickListener true
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

    private fun removeFromCart() {
        mCartAdapter.selectedItem.observe(viewLifecycleOwner) {
            cartViewModel.removeFromCart(it)
        }
    }

    override fun onProductItemClick(view: View, position: Int) {
        cartViewModel.recordList.observe(viewLifecycleOwner) {
            val action =
                CartFragmentDirections.actionCartFragmentToProductDetailFragment(it[position].product!!)
            findNavController().navigate(action)
        }
    }

}