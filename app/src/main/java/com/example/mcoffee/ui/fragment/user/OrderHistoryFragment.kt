package com.example.mcoffee.ui.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.FragmentOrderHistoryBinding
import com.example.mcoffee.ui.adapter.OrderHistoryAdapter
import com.example.mcoffee.ui.adapter.ViewPagerAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderHistoryViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : BaseFragment<FragmentOrderHistoryBinding>(FragmentOrderHistoryBinding::inflate) {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private lateinit var mAdapter: OrderHistoryAdapter

    private val tabItems = listOf(
        "Chờ xác nhận",
        "Đã xác nhận",
        "Đã hủy"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderHistoryViewModel.getOrderList()
        mAdapter = OrderHistoryAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        orderHistoryViewModel.orderList.observe(viewLifecycleOwner) {
        }
    }

    override fun bindView() {
        super.bindView()

        //set up tab layout and viewpager
        setUpViewPager(binding.viewPager, this@OrderHistoryFragment)
        setUpTabLayoutWithViewPager(
            binding.tabLayout,
            binding.viewPager
        )

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpTabLayoutWithViewPager(tabLayout: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabItems[position]
        }.attach()
    }

    private fun setUpViewPager(viewPager: ViewPager2, fragment: Fragment) {
        val adapter = ViewPagerAdapter(fragment, 3)
        viewPager.adapter = adapter
    }

}