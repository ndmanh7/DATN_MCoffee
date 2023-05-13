package com.example.mcoffee.ui.fragment.admin

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentEditCategoryAdminBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.admin.AdminCategoryManageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminEditCategoryFragment : BaseFragment<FragmentEditCategoryAdminBinding>(FragmentEditCategoryAdminBinding::inflate){

    private val args: AdminEditCategoryFragmentArgs by navArgs()
    private val adminCategoryManageViewModel: AdminCategoryManageViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            adminCategoryManageViewModel.editCategoryState.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Cập nhật danh mục thành công", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            adminCategoryManageViewModel.deleteCategoryState.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Xóa danh mục thành công", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun bindView() {
        super.bindView()
        val category = args.category
        binding.edtCategoryName.setText(category.categoryName)

        //update button
        binding.btnEditCategory.setOnClickListener {
            adminCategoryManageViewModel.editCategory(
                category,
                hashMapOf("/categoryName" to binding.edtCategoryName.text.toString())
            )
        }

        //delete button
        binding.btnRemoveProduct.setOnClickListener {
            showDialog(category)
        }
    }

    private fun showDialog(category: Category) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Xóa danh mục")
            .setMessage("Xác nhận xóa danh mục này?")
            .setPositiveButton("Xác nhận") { _, _ ->
                adminCategoryManageViewModel.deleteCategory(category)
            }
            .setNegativeButton("Hủy bỏ") { _, _ -> }
            .create()

        alertDialog.show()
    }

}