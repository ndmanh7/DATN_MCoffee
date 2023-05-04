package com.example.mcoffee.ui.fragment.viewpager

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.FragmentOrderStateBinding
import com.example.mcoffee.ui.adapter.OrderHistoryAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotConfirmedOrderFragment: BaseFragment<FragmentOrderStateBinding>(FragmentOrderStateBinding::inflate) {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private lateinit var mAdapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = OrderHistoryAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        orderHistoryViewModel.orderList.observe(viewLifecycleOwner) {
            val notConfirmedOrderList = mutableListOf<Order>()
            for (order in it) {
                if (!order.isConfirmed) {
                    notConfirmedOrderList.add(order)
                }
            }

            updateUI(notConfirmedOrderList)
        }
    }

    private fun updateUI(notConfirmedOrderList: MutableList<Order>) {
        mAdapter.submitList(notConfirmedOrderList)
        binding.recyclerViewOrderByState.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun bindView() {
        super.bindView()
        orderHistoryViewModel.getOrderList()
    }
}