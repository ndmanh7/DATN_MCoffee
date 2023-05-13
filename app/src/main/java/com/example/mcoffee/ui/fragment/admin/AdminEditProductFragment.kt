package com.example.mcoffee.ui.fragment.admin

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.R
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mcoffee.data.model.Order
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentEditProductBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.HomeViewModel
import com.example.mcoffee.ui.viewmodel.admin.AdminEditProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminEditProductFragment : BaseFragment<FragmentEditProductBinding>(FragmentEditProductBinding::inflate),
    AdapterView.OnItemSelectedListener{

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val editProductViewModel: AdminEditProductViewModel by viewModels()
    private val args: AdminEditProductFragmentArgs by navArgs()

    private var imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.imgProductImage.setImageURI(uri)
            editProductViewModel.getImage(uri)
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        //get category list
        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            setUpSpinner(it)
        }

        //handle remove product state
        lifecycleScope.launch {
            editProductViewModel.removeProductState.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            editProductViewModel.editProductState.collect {isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun bindView() {
        super.bindView()
        val productInfo = args.productInfo
        updateUI(productInfo)


        binding.apply {

            //back button
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            //edit product button
            btnEditProduct.setOnClickListener {
                if (editProductViewModel.productImageUri.value != null) {
                    productInfo.image = editProductViewModel.productImageUri.value.toString()
                }

                val updatedInformation = hashMapOf<String, Any?>(
                    "/productName" to edtProductName.text.toString(),
                    "/description" to edtProductDescription.text.toString(),
                    "/price" to edtProductPrice.text.toString().toInt()
                )
                editProductViewModel.editProduct(productInfo, updatedInformation)
            }

            //chose image
            imgProductImage.setOnClickListener {
                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            //remove product
            btnRemoveProduct.setOnClickListener {
               showDialog(productInfo)
            }
        }
    }

    private fun updateUI(productInfo: Product) {
        binding.apply {
            edtProductName.setText(productInfo.productName)
            edtProductDescription.setText(productInfo.description)
            edtProductPrice.setText(productInfo.price.toString())
            Glide.with(requireContext())
                .load(productInfo.image)
                .into(imgProductImage)
        }
    }

    private fun showDialog(productInfo: Product) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Xóa sản phẩm")
            .setMessage("Xác nhận xóa sản phẩm?")
            .setPositiveButton("Xác nhận") { _, _ ->
                editProductViewModel.removeProduct(productInfo)
            }
            .setNegativeButton("Hủy bỏ") { _, _ -> }
            .create()

        alertDialog.show()
    }

    private fun setUpSpinner(list: List<Category>) {

        val categoryNameList = mutableListOf<String>()
        for (category in list) {
            categoryNameList.add(category.categoryName)
        }

        binding.spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            categoryNameList
        )
        binding.spinnerCategory.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.tvCategory.text = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}