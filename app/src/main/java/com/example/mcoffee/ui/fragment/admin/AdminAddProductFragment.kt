package com.example.mcoffee.ui.fragment.admin

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentAddProductBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.HomeViewModel
import com.example.mcoffee.ui.viewmodel.admin.AdminHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminAddProductFragment :
    BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate),
    AdapterView.OnItemSelectedListener {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adminHomeViewModel: AdminHomeViewModel by viewModels()

    private var imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.imgProductImage.setImageURI(uri)
            adminHomeViewModel.getImage(uri)
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            setUpSpinner(it)
        }

        lifecycleScope.launch {
            adminHomeViewModel.addProductState.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun bindView() {
        super.bindView()
        binding.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            //add product
            btnAddProduct.setOnClickListener {
                validateInputField()

            }

            imgProductImage.setOnClickListener {
                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun validateInputField() {
        binding.apply {
            if (edtProductName.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Tên sản phẩm không được để trống", Toast.LENGTH_SHORT).show()
            } else if (edtProductDescription.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mô tả sản phẩm không được để trống", Toast.LENGTH_SHORT).show()
            } else if (edtProductPrice.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Giá sản phẩm không được để trống", Toast.LENGTH_SHORT).show()
            } else if (adminHomeViewModel.productImageUri.value?.toString() == null) {
                Toast.makeText(requireContext(), "Chưa có ảnh sản phẩm", Toast.LENGTH_SHORT).show()
            } else {
                var categoryId = ""
                for (category in homeViewModel.categoryList.value!!) {
                    if (tvCategory.text.toString() == category.categoryName) {
                        categoryId = category.uid
                    }
                }
                val product = Product(
                    productName = edtProductName.text.toString(),
                    description = edtProductDescription.text.toString(),
                    price = edtProductPrice.text.toString().toInt(),
                    categoryUid = categoryId,
                    image = adminHomeViewModel.productImageUri.value.toString()
                )
                adminHomeViewModel.addProductByAdmin(product)
            }

        }
    }

    private fun setUpSpinner(list: List<Category>) {

        val categoryNameList = mutableListOf<String>()
        for (category in list) {
            categoryNameList.add(category.categoryName)
        }

        binding.spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            categoryNameList
        )
        binding.spinnerCategory.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.tvCategory.text = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}