package com.example.mcoffee.ui.fragment.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.FragmentOrderStateBinding
import com.example.mcoffee.ui.adapter.OrderHistoryAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.fragment.user.OrderHistoryFragmentDirections
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
                if (order.isConfirmed == "processing") {
                    notConfirmedOrderList.add(order)
                }
            }
            updateUI(notConfirmedOrderList)
            if (notConfirmedOrderList.isNotEmpty()) {
                hideProgressBar()
                hideEmptyListView()
            } else {
                hideProgressBar()
                showEmptyListView()
            }
        }
    }

    private fun updateUI(notConfirmedOrderList: MutableList<Order>) {
        mAdapter.submitList(notConfirmedOrderList)
        binding.recyclerViewOrderByState.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            mAdapter.setOnItemClickListener {
                val action = OrderHistoryFragmentDirections.actionOrderHistoryFragmentToOrderDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    override fun bindView() {
        super.bindView()
        showProgressBar()
        orderHistoryViewModel.getOrderList()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showEmptyListView() {
        binding.emptyListView.visibility = View.VISIBLE
    }

    private fun hideEmptyListView() {
        binding.emptyListView.visibility = View.INVISIBLE
    }
}