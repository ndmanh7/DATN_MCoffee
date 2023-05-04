package com.example.mcoffee.ui.viewmodel.admin

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _addProductState = MutableSharedFlow<Boolean>()
    val addProductState = _addProductState.asSharedFlow()

    private val _productImageUri = MutableLiveData<Uri>()
    val productImageUri: LiveData<Uri> get() = _productImageUri

    fun addProductByAdmin(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            _addProductState.emit(productRepository.addProductByAdmin(product))
        }
    }

    fun getImage(uri: Uri) {
        viewModelScope.launch {
            _productImageUri.value = uri
        }
    }

}