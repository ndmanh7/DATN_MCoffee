package com.example.mcoffee.ui.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.FragmentOrderBinding
import com.example.mcoffee.ui.adapter.ProductListInOrderAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()
    private lateinit var mAdapter: ProductListInOrderAdapter

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.amount.observe(viewLifecycleOwner) { amount ->
            binding.apply {

            }
        }
    }

    override fun bindView() {
        super.bindView()

        //fetch data to views
        fetchProductInfo()

        binding.btnOrder.setOnClickListener {
            addOrder()
        }
    }

    private fun fetchProductInfo() {
        //get record info from detail screen
        val recordFromDetailFragment = requireArguments().getSerializable("record_from_detail_screen") as Record?

        //submit adapter list base on arguments
        mAdapter = ProductListInOrderAdapter()
        if (recordFromDetailFragment != null) {
            mAdapter.submitList(listOf(recordFromDetailFragment))
        } else {
            //get record info from cart screen
            val records = requireArguments().get("records") as List<*>
            mAdapter.submitList(records as List<Record>)
        }

        //set up adapter
        binding.rvProductList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        var totalAmount = 0
        var totalPrice = 0

        for (item in mAdapter.recordList) {
            totalAmount += item.amount
            totalPrice += item.totalPrice
        }

        binding.apply {
            tvTotalPayment.text = totalPrice.toString()
        }
    }


    private fun addOrder() {
        val records = requireArguments().get("records") as List<*>
        var totalPrice = 0

        for (item in records as List<Record>) {
            totalPrice += item.totalPrice
        }

        binding.apply {
            val order = Order()
            order.apply {
                orderDate = ""
                this.records = records
                this.totalPrice = totalPrice
            }
            viewModel.addToOrder(order)
        }
    }

}