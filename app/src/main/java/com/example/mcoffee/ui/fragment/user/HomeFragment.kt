package com.example.mcoffee.ui.fragment.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcoffee.R
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.databinding.FragmentHomeBinding
import com.example.mcoffee.ui.adapter.CategoryAdapter
import com.example.mcoffee.ui.adapter.ProductListAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.interfaces.IOnCategoryItemClickListener
import com.example.mcoffee.ui.interfaces.IOnProductItemClickListener
import com.example.mcoffee.ui.viewmodel.HomeViewModel
import com.example.mcoffee.ui.viewmodel.UserInformationViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    IOnCategoryItemClickListener,
    IOnProductItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()
    private val userInformationViewModel: UserInformationViewModel by activityViewModels()

    private var categoryList = arrayListOf<Category>()

    private lateinit var mCategoryAdapter: CategoryAdapter
    private lateinit var mProductAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getProductListByCategory(0)
        userInformationViewModel.getUserInfo()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        showCategoryList()
        showProductListByCategory()
    }

    override fun bindView() {
        super.bindView()
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun onCategoryItemClick(view: View, position: Int) {
        homeViewModel.getProductListByCategory(position)
    }

    private fun showCategoryList() {
        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            mCategoryAdapter = CategoryAdapter(it)
            binding.recyclerViewCategory.apply {
                adapter = mCategoryAdapter
                mCategoryAdapter.setClickListener(this@HomeFragment)
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

                mProductAdapter.setClickListener(this@HomeFragment)
            }
        }
    }

    override fun onProductItemClick(view: View, position: Int) {
        homeViewModel.productListByCategory.observe(viewLifecycleOwner) {
            val action =
                HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(it[position])
            findNavController().navigate(action)
        }
    }


}