package com.example.mcoffee.ui.fragment.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.FragmentStatisticsAdminBinding
import com.example.mcoffee.ui.adapter.admin.AdminTopSaleAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.HomeViewModel
import com.example.mcoffee.ui.viewmodel.OrderHistoryViewModel
import com.example.mcoffee.ui.viewmodel.admin.AdminTopSaleViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminStatisticsFragment :
    BaseFragment<FragmentStatisticsAdminBinding>(FragmentStatisticsAdminBinding::inflate), AdapterView.OnItemSelectedListener {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private val topSaleViewModel: AdminTopSaleViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var mTopSaleAdapter: AdminTopSaleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTopSaleAdapter = AdminTopSaleAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        super.observeViewModel()
        orderHistoryViewModel.oderListByDate.observe(viewLifecycleOwner) { orderListByYear ->
            val barChartData = arrayListOf<BarEntry>()
            for (month in 1..12) {
                barChartData.add(BarEntry(month.toFloat(), orderHistoryViewModel.getRevenueByMonth(orderListByYear, month.toString()).toFloat()))
            }
            setUpBarChart(barChartData)

            //total monthly revenue
            var totalRevenue = 0
            for (order in orderListByYear) {
                if (order.isConfirmed == "confirmed") {
                    totalRevenue += order.totalPrice
                }
            }
            binding.tvTotalMonthRevenue.text = "${totalRevenue}đ"

            val topSaleList = emptyList<Record>()
            topSaleViewModel.getTopSale(orderListByYear)
            setUpTopSaleList(orderListByYear)
        }
    }

    private fun setUpTopSaleList(orderListByYear: List<Order>) {
        mTopSaleAdapter.submitList(topSaleViewModel.getTopSale(orderListByYear))
        binding.recyclerViewTopSale.apply {
            adapter = mTopSaleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpBarChart(barChartData: ArrayList<BarEntry>) {
        val barDataSet = BarDataSet(barChartData, "Doanh thu")
        barDataSet.apply {
            color = ContextCompat.getColor(requireContext(), R.color.teal_200)
        }

        val barData = BarData(barDataSet)
        binding.barChart.apply {
            setFitBars(true)
            data = barData
            description.text = "Biểu đồ doanh thu theo từng tháng"
            description.textSize = 16F
            animateY(1500)
        }
    }

    override fun bindView() {
        super.bindView()
        setUpSpinner()

        //get products
    }

    private fun setUpSpinner() {
        val yearList = arrayListOf("2023","2022","2021","2020","2019")
        binding.spinnerYear.adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            yearList
        )
        binding.spinnerYear.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.tvYear.text = parent?.getItemAtPosition(position).toString()
        orderHistoryViewModel.getOrderListByDate(binding.tvYear.text.toString())

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}