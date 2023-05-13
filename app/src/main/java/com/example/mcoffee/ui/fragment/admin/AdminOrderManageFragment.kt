package com.example.mcoffee.ui.fragment.admin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.databinding.FragmentOrderManageAdminBinding
import com.example.mcoffee.ui.adapter.admin.AdminOrderAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

@AndroidEntryPoint
class AdminOrderManageFragment :
    BaseFragment<FragmentOrderManageAdminBinding>(FragmentOrderManageAdminBinding::inflate) {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private lateinit var mAdapter: AdminOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = AdminOrderAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        orderHistoryViewModel.oderListByDate.observe(viewLifecycleOwner) { orderList ->
            updateUI(orderList)
        }
    }

    private fun updateUI(orderList: List<Order>) {
        mAdapter.submitList(orderList)
        binding.recyclerViewOrderByDate.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            mAdapter.setOnItemClickListener {
                val action = AdminOrderManageFragmentDirections.actionAdminOrderManageFragmentToAdminOrderDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    override fun bindView() {
        super.bindView()
        getTodayOrder()
        getOrderByCustomDate()
        binding.btnDatePicker.setOnClickListener {
            showDatePicker()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTodayOrder() {
        val date = Calendar.getInstance().time
        val formattedTime = SimpleDateFormat("dd-MM-yyyy").format(date)

        //get today order
        binding.edtTime.setText(formattedTime.toString())
        orderHistoryViewModel.getOrderListByDate(
            binding.edtTime.text.toString()
        )
    }

    private fun getOrderByCustomDate() {
        //get order by custom date
        binding.edtTime.setOnEditorActionListener { textView, actionID, keyEvent ->
            if (actionID == EditorInfo.IME_ACTION_DONE) {
                orderHistoryViewModel.getOrderListByDate(
                    binding.edtTime.text.toString()
                )
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun showDatePicker() {
        binding.btnDatePicker.setOnClickListener {
            //date picker
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    run {
                        calendar.apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, monthOfYear)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }
                        val date = calendar.time
                        binding.edtTime.setText(SimpleDateFormat("dd-MM-yyyy").format(date))
                        orderHistoryViewModel.getOrderListByDate(
                            binding.edtTime.text.toString()
                        )
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

}