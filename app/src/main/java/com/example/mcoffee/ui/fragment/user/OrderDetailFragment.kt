package com.example.mcoffee.ui.fragment.user

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.databinding.FragmentOrderDetailAdminBinding
import com.example.mcoffee.ui.adapter.ProductsInOrderAdapter
import com.example.mcoffee.ui.adapter.admin.AdminOrderAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.fragment.admin.AdminOrderDetailFragmentArgs
import com.example.mcoffee.ui.viewmodel.admin.AdminConfirmOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailAdminBinding>(FragmentOrderDetailAdminBinding::inflate) {

    private val args: AdminOrderDetailFragmentArgs by navArgs()
    private lateinit var mProductsInOrderAdapter: ProductsInOrderAdapter

    private val adminConfirmOrderViewModel: AdminConfirmOrderViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        adminConfirmOrderViewModel.confirmState.observe(viewLifecycleOwner) {
            handleConfirmState(it)
        }

        adminConfirmOrderViewModel.abortState.observe(viewLifecycleOwner) {
            handleAbortState(it)
        }
    }

    private fun handleAbortState(fireBaseState: FireBaseState<String>) {
        when(fireBaseState) {
            is FireBaseState.Success -> {
                Toast.makeText(requireContext(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            is FireBaseState.Fail -> {
                Toast.makeText(requireContext(), fireBaseState.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleConfirmState(fireBaseState: FireBaseState<String>) {
        when(fireBaseState) {
            is FireBaseState.Success -> {
                Toast.makeText(requireContext(), "Xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            is FireBaseState.Fail -> {
                Toast.makeText(requireContext(), fireBaseState.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun bindView() {
        super.bindView()
        val orderInfo = args.order
        updateUI(orderInfo)

        binding.btnConfirm.visibility = View.GONE

        binding.btnAbort.setOnClickListener {
           showDialog(orderInfo)
        }
    }

    private fun showDialog(orderInfo: Order) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Hủy đơn hàng")
            .setMessage("Xác nhận hủy đơn hàng này?")
            .setPositiveButton("Xác nhận") { _, _ ->
                adminConfirmOrderViewModel.abortOrderByUser(orderInfo)
            }
            .setNegativeButton("Hủy bỏ") { _, _ -> }
            .create()

        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(orderInfo: Order) {
        mProductsInOrderAdapter = ProductsInOrderAdapter()
        mProductsInOrderAdapter.submitList(orderInfo.records)
        binding.apply {
            tvOrderId.text = orderInfo.uid
            tvUserName.text = orderInfo.receiverName
            tvUserPhone.text = orderInfo.receiverPhone
            tvUserAddress.text = orderInfo.receiverAddress
            tvOrderDate.text = orderInfo.orderDate
            tvOrderTotalPrice.text = orderInfo.totalPrice.toString() + " đ"

            when(orderInfo.isConfirmed) {
                "confirmed" -> {
                    tvOrderState.text = "Đã xác nhận"
                    tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.green))
                }
                "processing" -> {
                    tvOrderState.text = "Đang chờ xác nhận"
                    tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.green))

                }
                "aborted_by_admin" -> {
                    tvOrderState.text = "Đã hủy bởi quản trị viên"
                    tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.red))
                }

                "aborted_by_user" -> {
                    tvOrderState.text = "Đã hủy bởi khách hàng"
                    tvOrderState.setTextColor(ContextCompat.getColor(tvOrderState.context, R.color.red))
                }
            }

            recylerViewProductsInOrder.apply {
                adapter = mProductsInOrderAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

}