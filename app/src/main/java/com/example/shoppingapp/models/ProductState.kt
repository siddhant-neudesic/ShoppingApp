package com.example.shoppingapp.models

data class ProductState(
    val loading:Boolean = true,
    val product:Product  = Product(),
    val error:String? = null
)
