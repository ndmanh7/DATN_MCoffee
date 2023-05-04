package com.example.mcoffee.ui.fragment.admin

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentAddCategoryAdminBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.admin.AdminCategoryManageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminAddCategoryFragment : BaseFragment<FragmentAddCategoryAdminBinding>(FragmentAddCategoryAdminBinding::inflate) {

    private val adminCategoryManageViewModel: AdminCategoryManageViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            adminCategoryManageViewModel.addCategoryState.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun bindView() {
        super.bindView()
        binding.btnAddCategory.setOnClickListener {
            adminCategoryManageViewModel.addCategory(
                Category(
                    categoryName = binding.edtCategoryName.text.toString()
                )
            )
        }
    }

}