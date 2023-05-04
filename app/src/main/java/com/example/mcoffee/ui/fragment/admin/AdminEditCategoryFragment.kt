package com.example.mcoffee.ui.fragment.admin

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
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
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            adminCategoryManageViewModel.deleteCategoryState.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Xóa danh mục thành công", Toast.LENGTH_SHORT).show()
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
            adminCategoryManageViewModel.deleteCategory(category)
        }
    }

}