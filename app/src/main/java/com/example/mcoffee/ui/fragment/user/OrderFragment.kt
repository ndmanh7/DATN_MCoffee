package com.example.mcoffee.ui.fragment.user

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Record
import com.example.mcoffee.databinding.FragmentOrderBinding
import com.example.mcoffee.ui.adapter.ProductListInOrderAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by activityViewModels()
    private lateinit var mAdapter: ProductListInOrderAdapter

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.amount.observe(viewLifecycleOwner) { amount ->
            binding.apply {

            }
        }

        viewModel.address.observe(viewLifecycleOwner) {
            binding.apply {
                tvReceiverInfo.text = "${it["name"]} | ${it["phoneNumber"]}"
                tvReceiverAddress.text = it["address"]
            }
        }
    }

    override fun bindView() {
        super.bindView()

        //fetch data to views
        fetchProductInfo()

        binding.btnOrder.setOnClickListener {
            addOrder()
            findNavController().popBackStack()
        }

        binding.btnEditAddress.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_editAddressFragment)
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


    @SuppressLint("SimpleDateFormat")
    private fun addOrder() {
        val recordsFromCartFragment = requireArguments().get("records") as List<*>?
        val recordsFromDetailScreen = requireArguments().get("record_from_detail_screen") as Record?
        var records : MutableList<Record>? = mutableListOf()
        var totalPrice = 0

        if (recordsFromCartFragment != null) {
            records = recordsFromCartFragment as MutableList<Record>?
        } else {
            if (recordsFromDetailScreen != null) {
                records?.add(recordsFromDetailScreen)
            }
        }

        for (item in records as List<Record>) {
            totalPrice += item.totalPrice
        }

        binding.apply {
            val order = Order()
            val date = Calendar.getInstance().time
            val formatted = SimpleDateFormat("dd-MM-yyyy HH:mm").format(date)
            Log.d("manh", "addOrder at line 115: $formatted")
            order.apply {
                receiverName = viewModel.address.value?.get("name") ?: ""
                receiverPhone = viewModel.address.value?.get("phoneNumber") ?: ""
                receiverAddress = viewModel.address.value?.get("address") ?: ""
                orderDate = ""
                this.records = records
                this.totalPrice = totalPrice
                this.orderDate = formatted
            }
            viewModel.addToOrder(order)
        }
    }

}