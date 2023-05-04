package com.example.mcoffee.ui.fragment.admin

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentCategoryManageAdminBinding
import com.example.mcoffee.ui.adapter.admin.AdminCategoryAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.HomeViewModel
import com.example.mcoffee.ui.viewmodel.admin.AdminCategoryManageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminCategoryManageFragment : BaseFragment<FragmentCategoryManageAdminBinding>(FragmentCategoryManageAdminBinding::inflate) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adminCategoryManageViewModel: AdminCategoryManageViewModel by viewModels()
    private lateinit var mCategoryAdapter: AdminCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCategoryAdapter = AdminCategoryAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(categoryList: ArrayList<Category>) {
        mCategoryAdapter.submitList(categoryList)
        binding.recyclerViewCategoryAdmin.apply {
            adapter = mCategoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            mCategoryAdapter.setOnItemClickListener {
                val action = AdminCategoryManageFragmentDirections.actionAdminCategoryManageFragmentToAdminEditCategoryFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    override fun bindView() {
        super.bindView()

        //get all category
        homeViewModel.getAllCategories()

        //add button
        binding.btnAddCategory.setOnClickListener {
            findNavController().navigate(R.id.action_adminCategoryManageFragment_to_adminAddCategoryFragment)
        }
    }

}