package com.example.mcoffee.ui.main

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentHomeBinding
import com.example.mcoffee.ui.adapter.CategoryAdapter
import com.example.mcoffee.ui.adapter.ProductListAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.interfaces.IOnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    IOnItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    private var categoryList = arrayListOf<Category>()

    private lateinit var mCategoryAdapter: CategoryAdapter
    private lateinit var mProductAdapter: ProductListAdapter

    override fun observeViewModel() {
        super.observeViewModel()
        showCategoryList()
        showProductListByCategory()
    }
    override fun bindView() {
        super.bindView()
        homeViewModel.getAllCategories()
    }

    override fun onItemClick(view: View, position: Int) {
        homeViewModel.getProductListByCategory(position)
    }

    private fun showCategoryList() {
        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            mCategoryAdapter = CategoryAdapter(it)
            binding.recyclerViewCategory.apply {
                adapter = mCategoryAdapter
                mCategoryAdapter.setClickListener(this@HomeFragment)
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
        }
    }

    private fun showProductListByCategory() {
        homeViewModel.productListByCategory.observe(viewLifecycleOwner) {
            mProductAdapter = ProductListAdapter(it)
            binding.recyclerViewProduct.apply {
                adapter = mProductAdapter
                layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            }
        }
    }

}