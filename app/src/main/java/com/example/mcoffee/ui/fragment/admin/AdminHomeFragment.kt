package com.example.mcoffee.ui.fragment.admin

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.R
import com.example.mcoffee.databinding.FragmentHomeAdminBinding
import com.example.mcoffee.ui.adapter.CategoryAdapter
import com.example.mcoffee.ui.adapter.ProductListAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.interfaces.IOnCategoryItemClickListener
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener
import com.example.mcoffee.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminHomeFragment : BaseFragment<FragmentHomeAdminBinding>(FragmentHomeAdminBinding::inflate),
    IOnCategoryItemClickListener,
    IOnProductItemClickListener{

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var mCategoryAdapter: CategoryAdapter
    private lateinit var mProductAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getProductListByCategory(0)

    }

    override fun bindView() {
        super.bindView()
        homeViewModel.getProductListByCategory(0)
        showCategoryList()
        showProductListByCategory()

        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminAddProductFragment())
        }
    }

    private fun showCategoryList() {
        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            mCategoryAdapter = CategoryAdapter(it)
            binding.recyclerViewCategory.apply {
                adapter = mCategoryAdapter
                mCategoryAdapter.setClickListener(this@AdminHomeFragment)
                layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }
        }
    }

    private fun showProductListByCategory() {
        homeViewModel.productListByCategory.observe(viewLifecycleOwner) {
            mProductAdapter = ProductListAdapter(it)
            binding.recyclerViewProduct.apply {
                adapter = mProductAdapter
                layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

                mProductAdapter.setClickListener(this@AdminHomeFragment)
            }
        }
    }

    override fun onCategoryItemClick(view: View, position: Int) {
        homeViewModel.getProductListByCategory(position)
    }

    override fun onProductItemClick(view: View, position: Int) {
        homeViewModel.productListByCategory.observe(viewLifecycleOwner) {
            val action = AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminEditProductFragment(it[position])
            findNavController().navigate(action)
        }
    }



}