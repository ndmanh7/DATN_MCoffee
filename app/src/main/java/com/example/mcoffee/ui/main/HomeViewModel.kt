package com.example.mcoffee.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.data.repo.category.CategoryRepository
import com.example.mcoffee.data.repo.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _categoryList = MutableLiveData<ArrayList<Category>>()
    val categoryList: LiveData<ArrayList<Category>> get() = _categoryList

    private val _productListByCategory = MutableLiveData<ArrayList<Product>>()
    val productListByCategory: LiveData<ArrayList<Product>> get() = _productListByCategory


    init {
        getProductListByCategory(0)
    }

    fun getAllCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect {
                _categoryList.value = it
            }
        }
    }

    fun getProductListByCategory(position: Int) {
        viewModelScope.launch {
            val category = categoryList.value?.get(position)
            category?.let {
                productRepository.getProductsByCategory(it).collect { productList ->
                    _productListByCategory.value = productList
                }
            }
        }
    }


}