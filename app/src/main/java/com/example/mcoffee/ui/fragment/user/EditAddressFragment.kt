package com.example.mcoffee.ui.fragment.user

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.databinding.FragmentEditAddressBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAddressFragment : BaseFragment<FragmentEditAddressBinding>(FragmentEditAddressBinding::inflate) {

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        orderViewModel.address.observe(viewLifecycleOwner) {
            binding.apply {
                edtName.setText(it["name"])
                edtPhone.setText(it["phoneNumber"])
                edtAddress.setText(it["address"])
            }
        }
    }

    override fun bindView() {
        super.bindView()
        binding.apply {
            btnDone.setOnClickListener {
                orderViewModel.addAddress(
                    edtName.text.toString(),
                    edtPhone.text.toString(),
                    edtAddress.text.toString()
                )
                findNavController().popBackStack()
            }

            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}