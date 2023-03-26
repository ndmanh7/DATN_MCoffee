package com.example.mcoffee.data.model.category

sealed class CategoryRequestState {
    object Success : CategoryRequestState()
    object Fail : CategoryRequestState()
}