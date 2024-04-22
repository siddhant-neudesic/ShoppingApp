package com.example.shoppingapp.models

data class ProductsState(
    val loading:Boolean = true,
    val list:List<Product>  = emptyList(),
    val error:String? = null
)