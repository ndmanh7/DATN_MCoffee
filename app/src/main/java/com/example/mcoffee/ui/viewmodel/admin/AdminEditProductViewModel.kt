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
class AdminEditProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel(){

    private var _editProductState = MutableSharedFlow<Boolean>()
    val editProductState = _editProductState.asSharedFlow()

    private var _removeProductState = MutableSharedFlow<Boolean>()
    val removeProductState = _removeProductState.asSharedFlow()

    private val _productImageUri = MutableLiveData<Uri>()
    val productImageUri: LiveData<Uri> get() = _productImageUri

    fun editProduct(product: Product, updatedInformation: HashMap<String, Any?>) {
        viewModelScope.launch(Dispatchers.IO) {
            _editProductState.emit(productRepository.editProductByAdmin(product, updatedInformation))
        }
    }


    fun getImage(uri: Uri) {
        viewModelScope.launch {
            _productImageUri.value = uri
        }
    }


    fun removeProduct(product: Product) {
        viewModelScope.launch {
            _removeProductState.emit(productRepository.removeProductByAdmin(product))
        }
    }


}