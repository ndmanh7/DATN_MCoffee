package com.example.mcoffee.ui.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcoffee.data.model.category.Category
import com.example.mcoffee.domain.repo.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminCategoryManageViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _addCategoryState = MutableSharedFlow<Boolean>()
    val addCategoryState = _addCategoryState.asSharedFlow()

    private var _editCategoryState = MutableSharedFlow<Boolean>()
    val editCategoryState = _editCategoryState.asSharedFlow()

    private var _deleteCategoryState = MutableSharedFlow<Boolean>()
    val deleteCategoryState = _deleteCategoryState.asSharedFlow()

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            _addCategoryState.emit(categoryRepository.addCategoryByAdmin(category))
        }
    }

    fun editCategory(category: Category, newInformation: HashMap<String, Any?>) {
        viewModelScope.launch(Dispatchers.IO) {
            _editCategoryState.emit(categoryRepository.editCategoryByAdmin(category, newInformation))
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteCategoryState.emit(categoryRepository.deleteCategoryByAdmin(category))
        }
    }


}